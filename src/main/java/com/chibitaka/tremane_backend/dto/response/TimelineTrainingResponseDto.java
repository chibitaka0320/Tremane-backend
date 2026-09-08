package com.chibitaka.tremane_backend.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/** タイムライントレーニング記録用DTO */
@Data
public class TimelineTrainingResponseDto {
    private String userId; // ユーザーID
    private String nickname; // ニックネーム
    private LocalDate date; // トレーニング日付
    private List<TimelineBodyParts> bodyParts; // 部位一覧
    private Integer exerciseCount; // 種目数
    private Integer totalVolume; // 総負荷量
    private Integer estimatedCalories; // 推定消費カロリー（体重未設定の場合はnull）
    private LocalDateTime lastActivityAt; // 最終セット登録日時

    @Data
    public static class TimelineBodyParts {
        private Integer partsId;
        private String bodyPartsName;
    }
}
