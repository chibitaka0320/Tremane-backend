package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;

import lombok.Data;

/** eatingsテーブル用エンティティ */
@Data
public class EatingEntity {
    private Long eatingId;
    private LocalDate date;
    private Long userId;
    private String name;
    private double protein;
    private double fat;
    private double carbo;
}
