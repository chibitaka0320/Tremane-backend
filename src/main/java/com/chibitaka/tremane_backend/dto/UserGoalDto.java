package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザー目標DTO */
@Data
public class UserGoalDto {
    private String userId;
    private Double weight;
    private Double goalWeight;
    private LocalDate start;
    private LocalDate finish;
    private Integer pfc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
