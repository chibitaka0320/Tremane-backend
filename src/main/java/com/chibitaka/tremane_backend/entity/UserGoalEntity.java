package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;

import lombok.Data;

/** ユーザー目標テーブルエンティティ */
@Data
public class UserGoalEntity {
    private Integer userId;
    private Double weight;
    private Double goalWeight;
    private LocalDate start;
    private LocalDate finish;
    private Integer pfc;
}
