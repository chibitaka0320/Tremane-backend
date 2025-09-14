package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** ユーザー目標用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGoalDto {
    private String userId; // ユーザーID
    private Double weight; // 体重
    private Double goalWeight; // 目標体重
    private LocalDate start; // 開始日
    private LocalDate finish; // 終了日
    private Integer pfc; // PFC
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
