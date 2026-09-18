package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 食事用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EatingDto {
    private String eatingId; // 食事ID
    private LocalDate date; // 日付
    private String userId; // ユーザーID
    private String name; // 名前
    private int calories; // カロリー
    private double protein; // タンパク質
    private double fat; // 脂質
    private double carbo; // 糖質
    private String mealId; // 所属する食事記録ID（単独の食品記録の場合はnull）
    private String unit; // 単位（g / count）
    private Double quantity; // 数量（単位に対応する数値。参考情報でありカロリー等の算出には使用しない）
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
