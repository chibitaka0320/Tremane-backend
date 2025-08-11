package com.chibitaka.tremane_backend.entity;

import lombok.Data;

@Data
public class ExerciseEntity {
    private String exerciseId;
    private Long partsId;
    private String name;
}
