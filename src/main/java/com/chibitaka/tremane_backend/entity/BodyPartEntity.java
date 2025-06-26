package com.chibitaka.tremane_backend.entity;

import java.util.List;

import lombok.Data;

@Data
public class BodyPartEntity {
    private Long partsId;
    private String name;
    private List<ExerciseEntity> exercises;
}
