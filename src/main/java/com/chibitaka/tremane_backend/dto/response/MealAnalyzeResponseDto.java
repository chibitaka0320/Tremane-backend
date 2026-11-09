package com.chibitaka.tremane_backend.dto.response;

import java.util.List;

import com.chibitaka.tremane_backend.dto.MealAnalysisDto;

/** 食事写真解析APIレスポンス（解析結果＋本日の残り解析回数） */
public record MealAnalyzeResponseDto(
        String mealName,
        List<MealAnalysisDto.Food> foods,
        int remainingCount) {
}
