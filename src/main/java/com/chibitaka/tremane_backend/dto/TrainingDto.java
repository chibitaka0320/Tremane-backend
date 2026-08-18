package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** トレーニング用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {
    private String trainingId; // トレーニングID
    private LocalDate date; // 日付
    private String userId; // ユーザーID
    private String exerciseId; // 種目ID
    private double weight; // 重量
    private int reps; // 回数
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
