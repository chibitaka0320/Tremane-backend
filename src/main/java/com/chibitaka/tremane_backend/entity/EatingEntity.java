package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** eatingsテーブル用エンティティ */
@Data
public class EatingEntity {
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
