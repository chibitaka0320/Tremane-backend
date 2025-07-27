package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザー目標テーブルエンティティ */
@Data
public class UserGoalEntity {
    private String userId;
    private Double weight;
    private Double goalWeight;
    private LocalDate start;
    private LocalDate finish;
    private Integer pfc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
