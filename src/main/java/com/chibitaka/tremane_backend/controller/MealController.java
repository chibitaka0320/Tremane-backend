package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.MealDto;
import com.chibitaka.tremane_backend.form.MealForm;
import com.chibitaka.tremane_backend.service.MealService;

import lombok.RequiredArgsConstructor;

/** 食事記録用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService; // 食事記録Service

    /** ユーザー食事記録更新情報取得 */
    @GetMapping("")
    public ResponseEntity<List<MealDto>> getMeals(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        List<MealDto> mealDtos = mealService.getMealsByUserId(userId, updatedAt);

        return ResponseEntity.status(HttpStatus.OK).body(mealDtos);
    }

    /** 食事記録詳細取得 */
    @GetMapping("/{mealId}")
    public ResponseEntity<MealDto> getMealDetail(@PathVariable String mealId) {
        String userId = UserInfo.getUserId();
        MealDto mealDto = mealService.getMealById(userId, mealId);

        return ResponseEntity.status(HttpStatus.OK).body(mealDto);
    }

    /** 食事記録追加更新 */
    @PostMapping("")
    public ResponseEntity<Void> saveMeals(@RequestBody MealForm[] forms) {
        String userId = UserInfo.getUserId();
        mealService.saveMeals(userId, forms);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** 食事記録削除 */
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable String mealId) {
        String userId = UserInfo.getUserId();
        mealService.deleteMeal(userId, mealId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
