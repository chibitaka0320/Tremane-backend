package com.chibitaka.tremane_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.MealAnalysisDto;
import com.chibitaka.tremane_backend.form.MealAnalyzeForm;
import com.chibitaka.tremane_backend.service.MealAnalyzeService;

import lombok.RequiredArgsConstructor;

/** 食事写真解析用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/meals")
public class MealAnalyzeController {

    private final MealAnalyzeService mealAnalyzeService; // 食事写真解析Service

    /** 食事写真解析（推定結果を返すのみで、保存は行わない） */
    @PostMapping("/analyze")
    public ResponseEntity<MealAnalysisDto> analyze(@RequestBody @Validated MealAnalyzeForm form) {
        String userId = UserInfo.getUserId();
        MealAnalysisDto result = mealAnalyzeService.analyze(userId, form);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
