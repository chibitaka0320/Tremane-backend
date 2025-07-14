package com.chibitaka.tremane_backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class TrainingRecordDto {
    private long partsId;
    private String name;
    private List<Exercise> exercises;

    @Data
    public static class Exercise {
        private long exerciseId;
        private String name;
        private List<Set> sets;
    }

    @Data
    public static class Set {
        private Long trainingId;
        private Integer weight;
        private Integer reps;
    }
}
