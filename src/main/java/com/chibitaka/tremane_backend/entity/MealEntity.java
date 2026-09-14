package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 食事記録テーブルEntity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealEntity {
    private String mealId; // 食事記録ID
    private LocalDate date; // 日付
    private String userId; // ユーザーID
    private String name; // 食事名
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
