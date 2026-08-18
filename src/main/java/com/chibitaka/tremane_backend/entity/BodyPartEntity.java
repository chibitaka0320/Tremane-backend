package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** トレーニング部位テーブルEntity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyPartEntity {
    private Long partsId; // 部位ID
    private String name; // 部位名
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
