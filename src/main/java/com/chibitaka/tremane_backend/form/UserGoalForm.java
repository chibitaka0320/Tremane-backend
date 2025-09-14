package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザー目標追加更新用Form */
@Data
public class UserGoalForm {
    private String userId; // ユーザーID
    private Double weight; // 体重
    private Double goalWeight; // 目標体重
    private LocalDate start; // 開始日
    private LocalDate finish; // 終了日
    private Integer pfc; // PFC
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
