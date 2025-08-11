package com.chibitaka.tremane_backend.entity;

import java.util.List;

import lombok.Data;

@Data
public class TrainingRecordEntity {
    private long partsId;
    private String name;
    private List<ExerciseEntity> exercises;

    @Data
    public static class ExerciseEntity {
        private String exerciseId;
        private String name;
        private List<SetEntity> sets;
    }

    @Data
    public static class SetEntity {
        private String trainingId;
        private Integer weight;
        private Integer reps;
    }
}
