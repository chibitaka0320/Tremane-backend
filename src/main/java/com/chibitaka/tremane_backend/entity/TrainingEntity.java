package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** トレーニングエンティティ */
@Data
public class TrainingEntity {
    private String trainingId;
    private LocalDate date;
    private String userId;
    private Long exerciseId;
    private double weight;
    private int reps;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
