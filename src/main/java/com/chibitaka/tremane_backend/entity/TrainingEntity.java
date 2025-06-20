package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** トレーニングエンティティ */
@Data
public class TrainingEntity {
    private Long trainingId;
    private LocalDate date;
    private Long userId;
    private Long exerciseId;
    private int weight;
    private int reps;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
