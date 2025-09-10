package com.chibitaka.tremane_backend.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/** タイムライントレーニング記録用DTO */
@Data
public class TimelineTrainingResponseDto {
    private String userId; // ユーザーID
    private LocalDate date; // トレーニング日付
    private List<TimelineBodyParts> bodyParts; // 部位一覧

    @Data
    public static class TimelineBodyParts {
        private Integer partsId;
        private String bodyPartsName;
    }
}
