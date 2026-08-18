package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** トレーニング追加更新用Form */
@Data
public class TrainingForm {
    private String trainingId; // トレーニングID
    private LocalDate date; // 日付
    private String exerciseId; // 種目ID
    private double weight; // 重量
    private int reps; // 回数
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
