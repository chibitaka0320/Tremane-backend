package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;

import lombok.Data;

/** ユーザー目標DTO */
@Data
public class UserGoalDto {
    private Double weight;
    private Double goalWeight;
    private Integer goalCalorie;
    private LocalDate start;
    private LocalDate finish;
    private Integer pfc;
}
