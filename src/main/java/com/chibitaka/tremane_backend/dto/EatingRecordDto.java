package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/** 食事記録取得用DTO */
@Data
public class EatingRecordDto {

    private LocalDate date;
    private List<Exercise> exercises;

    @Data
    public static class Exercise {
        private String name;
        private List<Set> sets;
    }

    @Data
    public static class Set {
        private Long trainingId;
        private Integer weight;
        private Integer reps;
    }

    /** 合計 */
    @Data
    private static class totalDto {
        private int calories;
        private double protein;
        private double fat;
        private double carbo;
    }

    /** 目標 */
    @Data
    private static class goalDto {
        private int calories;
        private double protein;
        private double fat;
        private double carbo;
    }

    /** 食事 */
    @Data
    private static class MealDto {
        private Long eatingId;
        private String name;
        private int calories;
        private double protein;
        private double fat;
        private double carbo;
    }

}
