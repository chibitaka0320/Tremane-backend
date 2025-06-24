package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TrainingForm {
    private LocalDate date;
    private Long exerciseId;
    private double weight;
    private int reps;
}
