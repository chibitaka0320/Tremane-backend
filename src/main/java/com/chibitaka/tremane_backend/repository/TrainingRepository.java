package com.chibitaka.tremane_backend.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.TrainingDto;
import com.chibitaka.tremane_backend.dto.response.TrainingResponseDto;
import com.chibitaka.tremane_backend.entity.TrainingEntity;
import com.chibitaka.tremane_backend.entity.TrainingRecordEntity;

@Mapper
public interface TrainingRepository {

    /** トレーニング記録更新情報取得 */
    List<TrainingDto> getTrainings(String userId, LocalDateTime updatedAt);

    /** ユーザーID、日付検索 */
    List<TrainingRecordEntity> findByUserIdAndDate(TrainingEntity entity);

    /** トレーニングID検索 */
    TrainingResponseDto findById(String trainingId);

    /** トレーニング記録追加 */
    int upsertTraining(TrainingEntity entity);

    /** トレーニング記録更新 */
    int update(TrainingEntity entity);

    /** トレーニング記録削除 */
    int delete(String userId, String trainingId);

    /** 月別トレーニング日数取得 */
    List<Map<String, Object>> getMonthlyTrainingCount(List<String> userIds, LocalDate startDate, LocalDate endDate);
}
