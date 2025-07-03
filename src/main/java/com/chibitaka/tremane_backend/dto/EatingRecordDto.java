package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/** 食事記録取得用DTO */
@Data
public class EatingRecordDto {

    private LocalDate date;
    private TotalDto total;
    private GoalDto goal;
    private RateDto rate;
    private List<EatingDto> meals;

    /** 合計 */
    @Data
    public static class TotalDto {
        private int calories;
        private double protein;
        private double fat;
        private double carbo;
    }

    /** 目標 */
    @Data
    public static class GoalDto {
        private int calories;
        private double protein;
        private double fat;
        private double carbo;
    }

    /** 達成率 */
    @Data
    public static class RateDto {
        private double protein;
        private double fat;
        private double carbo;
    }
}
