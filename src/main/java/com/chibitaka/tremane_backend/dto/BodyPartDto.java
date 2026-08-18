package com.chibitaka.tremane_backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** トレーニング部位用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyPartDto {
    private Long partsId; // 部位ID
    private String name; // 名前
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
