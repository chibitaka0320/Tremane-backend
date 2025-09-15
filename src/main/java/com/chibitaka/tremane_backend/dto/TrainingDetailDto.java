package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** トレーニング詳細用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDetailDto {
    private String trainingId; // トレーニングID
    private LocalDate date; // 日付
    private int partsId; // 部位ID
    private String exerciseId; // 種目ID
    private double weight; // 重量
    private double reps; // 回数
}
