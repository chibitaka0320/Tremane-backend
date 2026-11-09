package com.chibitaka.tremane_backend.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.MealAnalysisDto;
import com.chibitaka.tremane_backend.dto.response.MealAnalyzeResponseDto;
import com.chibitaka.tremane_backend.form.MealAnalyzeForm;
import com.chibitaka.tremane_backend.service.analyzer.FoodImageAnalyzer;

import lombok.RequiredArgsConstructor;

/**
 * 食事写真解析Service
 * 画像は解析のためにメモリ上で扱うのみで、保存・ログ出力は行わない
 */
@Service
@RequiredArgsConstructor
public class MealAnalyzeService {

    /** 受け付ける画像形式 */
    private static final Set<String> ALLOWED_MEDIA_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

    /** 画像サイズ上限（デコード後） */
    private static final int MAX_IMAGE_BYTES = 5 * 1024 * 1024;

    private static final int DAILY_ANALYSIS_LIMIT = 5;
    private static final ZoneId ANALYSIS_ZONE = ZoneId.of("Asia/Tokyo");

    private final Map<String, Integer> dailyAnalysisCounts = new HashMap<>();
    private LocalDate analysisDate = LocalDate.now(ANALYSIS_ZONE);

    private final FoodImageAnalyzer foodImageAnalyzer; // 食事画像解析

    /** 食事写真解析（解析結果と本日の残り回数を返す） */
    public MealAnalyzeResponseDto analyze(String userId, MealAnalyzeForm form) {
        if (!ALLOWED_MEDIA_TYPES.contains(form.getMediaType())) {
            throw new ApiResponseException(400, "400", "対応していない画像形式です");
        }
        validateImage(form.getImage());

        reserveAnalysis(userId);
        MealAnalysisDto result = normalize(foodImageAnalyzer.analyze(form.getImage(), form.getMediaType()));

        return new MealAnalyzeResponseDto(result.mealName(), result.foods(), getRemainingCount(userId));
    }

    /** 本日の残り解析回数 */
    public synchronized int getRemainingCount(String userId) {
        resetIfNewDay();
        return Math.max(0, DAILY_ANALYSIS_LIMIT - dailyAnalysisCounts.getOrDefault(userId, 0));
    }

    /** 1日あたりの解析回数上限 */
    public int getDailyLimit() {
        return DAILY_ANALYSIS_LIMIT;
    }

    /** 日本時間で日付が変わっていれば回数をリセットする */
    private synchronized void resetIfNewDay() {
        LocalDate today = LocalDate.now(ANALYSIS_ZONE);
        if (!today.equals(analysisDate)) {
            dailyAnalysisCounts.clear();
            analysisDate = today;
        }
    }

    /** 同時リクエストも含め、ユーザーごとの解析回数を日本時間の日付で制限する */
    private synchronized void reserveAnalysis(String userId) {
        resetIfNewDay();
        int count = dailyAnalysisCounts.getOrDefault(userId, 0);
        if (count >= DAILY_ANALYSIS_LIMIT) {
            throw new ApiResponseException(429, "429", "食事写真の解析は1日5回までです");
        }
        dailyAnalysisCounts.put(userId, count + 1);
    }

    /** Base64として正しく、サイズ上限内であることを確認する */
    private void validateImage(String imageBase64) {
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(imageBase64);
        } catch (IllegalArgumentException e) {
            throw new ApiResponseException(400, "400", "画像データが不正です");
        }
        if (bytes.length == 0 || bytes.length > MAX_IMAGE_BYTES) {
            throw new ApiResponseException(400, "400", "画像サイズが大きすぎます");
        }
    }

    /** 解析結果を画面で扱いやすい形に整える（名前なし除外・負数を0・小数第1位に丸め） */
    private MealAnalysisDto normalize(MealAnalysisDto result) {
        List<MealAnalysisDto.Food> foods = result.foods() == null ? List.of()
                : result.foods().stream()
                        .filter(food -> food.name() != null && !food.name().isBlank())
                        .map(food -> new MealAnalysisDto.Food(
                                food.name().trim(),
                                round1(food.protein()),
                                round1(food.fat()),
                                round1(food.carbo())))
                        .toList();
        String mealName = result.mealName() == null ? "" : result.mealName().trim();

        return new MealAnalysisDto(mealName, foods);
    }

    private static double round1(double value) {
        return Math.round(Math.max(0, value) * 10) / 10.0;
    }
}
