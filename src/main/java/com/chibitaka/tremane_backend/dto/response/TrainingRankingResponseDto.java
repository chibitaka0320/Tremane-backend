package com.chibitaka.tremane_backend.dto.response;

import lombok.Data;

/** トレーニング日数ランキング用レスポンスDTO */
@Data
public class TrainingRankingResponseDto {
    private String userId; // ユーザーID
    private String nickname; // ニックネーム
    private int trainingCounts; // トレーニング日数
}
