package com.chibitaka.tremane_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.response.MealAnalyzeResponseDto;
import com.chibitaka.tremane_backend.dto.response.MealAnalyzeUsageResponseDto;
import com.chibitaka.tremane_backend.form.MealAnalyzeForm;
import com.chibitaka.tremane_backend.service.MealAnalyzeService;

import lombok.RequiredArgsConstructor;

/** 食事写真解析用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/meals/analyze")
public class MealAnalyzeController {

    private final MealAnalyzeService mealAnalyzeService; // 食事写真解析Service

    /** 食事写真解析（推定結果と本日の残り回数を返すのみで、保存は行わない） */
    @PostMapping("")
    public ResponseEntity<MealAnalyzeResponseDto> analyze(@RequestBody @Validated MealAnalyzeForm form) {
        String userId = UserInfo.getUserId();
        MealAnalyzeResponseDto result = mealAnalyzeService.analyze(userId, form);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /** 食事写真解析の利用状況（本日の残り回数）取得 */
    @GetMapping("/usage")
    public ResponseEntity<MealAnalyzeUsageResponseDto> getUsage() {
        String userId = UserInfo.getUserId();
        MealAnalyzeUsageResponseDto usage = new MealAnalyzeUsageResponseDto(
                mealAnalyzeService.getRemainingCount(userId),
                mealAnalyzeService.getDailyLimit());

        return ResponseEntity.status(HttpStatus.OK).body(usage);
    }
}
