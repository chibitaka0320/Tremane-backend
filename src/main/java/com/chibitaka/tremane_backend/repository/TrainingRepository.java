package com.chibitaka.tremane_backend.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.TrainingDetailDto;
import com.chibitaka.tremane_backend.entity.TrainingEntity;

/** トレーニングテーブル操作用インターフェース */
@Mapper
public interface TrainingRepository {

    /** トレーニング詳細取得（ID） */
    TrainingDetailDto findById(String trainingId);

    /** ユーザートレーニング一覧取得(更新情報、ID昇順) */
    List<TrainingEntity> findByUserId(String userId, LocalDateTime updatedAt);

    /** トレーニング追加更新 */
    int upsert(TrainingEntity entity);

    /** トレーニング削除 */
    int delete(String userId, String trainingId);

    /** ユーザー月別トレーニング日数取得 */
    List<Map<String, Object>> findMonthlyTrainingCountByUserIds(List<String> userIds, LocalDate startDate,
            LocalDate endDate);

    /** タイムライン情報取得 */
    List<Map<String, Object>> findTimelineByUserIds(List<String> userIds);
}
