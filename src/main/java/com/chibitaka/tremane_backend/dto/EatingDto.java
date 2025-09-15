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
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
