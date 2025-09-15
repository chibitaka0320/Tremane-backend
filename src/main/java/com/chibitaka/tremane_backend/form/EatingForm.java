package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** 食事追加更新用Form */
@Data
public class EatingForm {
    private String eatingId; // 食事ID
    private LocalDate date; // 日付
    private String userId; // ユーザーID
    private String name; // 食事名
    private int calories; // カロリー
    private double protein; // タンパク質
    private double fat; // 脂質
    private double carbo; // 糖質
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
