package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TrainingDto {
    private String trainingId;
    private LocalDate date;
    private String userId;
    private String exerciseId;
    private double weight;
    private double reps;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
