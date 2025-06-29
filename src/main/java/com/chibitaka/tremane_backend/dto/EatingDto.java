package com.chibitaka.tremane_backend.dto;

import lombok.Data;

@Data
public class EatingDto {
    private Long eatingId;
    private String name;
    private int calories;
    private double protein;
    private double fat;
    private double carbo;
}
