package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EatingDto {
    private Long eatingId;
    private LocalDate date;
    private String name;
    private int calories;
    private double protein;
    private double fat;
    private double carbo;
}
