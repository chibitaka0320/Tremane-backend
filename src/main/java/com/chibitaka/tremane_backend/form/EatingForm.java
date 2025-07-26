package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** 食事記録リクエストフォーム */
@Data
public class EatingForm {
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
