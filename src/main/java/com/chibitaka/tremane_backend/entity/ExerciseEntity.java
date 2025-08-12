package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExerciseEntity {
    private String exerciseId;
    private String ownerUserId;
    private Long partsId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
