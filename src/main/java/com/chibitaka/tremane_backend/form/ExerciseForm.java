package com.chibitaka.tremane_backend.form;

import java.time.LocalDateTime;

import lombok.Data;

/** マイ種目リクエストフォーム */
@Data
public class ExerciseForm {
    private String exerciseId;
    private String ownerUserId;
    private long partsId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
