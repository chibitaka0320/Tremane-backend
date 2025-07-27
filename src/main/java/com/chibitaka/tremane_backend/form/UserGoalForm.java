package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザー目標設定更新form */
@Data
public class UserGoalForm {
    private String userId;
    private Double weight;
    private Double goalWeight;
    private LocalDate start;
    private LocalDate finish;
    private Integer pfc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
