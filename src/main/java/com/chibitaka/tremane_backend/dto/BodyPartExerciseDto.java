package com.chibitaka.tremane_backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class BodyPartExerciseDto {
    private Long partsId;
    private String name;
    private List<ExerciseDto> exercises;

    @Data
    public static class ExerciseDto {
        private Long exerciseId;
        private String name;
    }
}
