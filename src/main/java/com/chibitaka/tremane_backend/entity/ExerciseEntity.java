package com.chibitaka.tremane_backend.entity;

import lombok.Data;

@Data
public class ExerciseEntity {
    private Long exerciseId;
    private Long partsId;
    private String name;
}
