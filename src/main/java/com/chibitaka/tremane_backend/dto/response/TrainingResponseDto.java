package com.chibitaka.tremane_backend.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TrainingResponseDto {
    private long trainingId;
    private LocalDate date;
    private int partsId;
    private int exerciseId;
    private double weight;
    private double reps;
}
