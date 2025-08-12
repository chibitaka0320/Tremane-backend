package com.chibitaka.tremane_backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExerciseDto {
    private String exerciseId;
    private String ownerUserId;
    private long partsId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
