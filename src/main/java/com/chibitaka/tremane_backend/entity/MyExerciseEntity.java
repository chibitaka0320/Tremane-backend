package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

/** マイ種目エンティティ */
@Data
public class MyExerciseEntity {
    private String exerciseId;
    private String userId;
    private Long partsId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
