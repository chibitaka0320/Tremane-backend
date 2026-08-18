package com.chibitaka.tremane_backend.form;

import java.time.LocalDateTime;

import lombok.Data;

/** トレーニング種目追加更新用Form */
@Data
public class ExerciseForm {
    private String exerciseId; // 種目ID
    private long partsId; // 部位ID
    private String name; // 種目名
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
