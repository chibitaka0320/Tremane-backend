package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** 食事記録追加更新用Form */
@Data
public class MealForm {
    private String mealId; // 食事記録ID
    private LocalDate date; // 日付
    private String userId; // ユーザーID
    private String name; // 食事名
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
