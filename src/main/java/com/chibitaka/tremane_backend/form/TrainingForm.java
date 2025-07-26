package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TrainingForm {
    private String trainingId;
    private LocalDate date;
    private Long exerciseId;
    private double weight;
    private int reps;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
