package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EatingDto {
    private String eatingId;
    private LocalDate date;
    private String userId;
    private String name;
    private int calories;
    private double protein;
    private double fat;
    private double carbo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
