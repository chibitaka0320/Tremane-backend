package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;

import lombok.Data;

/** 食事記録リクエストフォーム */
@Data
public class EatingForm {
    private LocalDate date;
    private Long userId;
    private String name;
    private double protein;
    private double fat;
    private double carbo;
}
