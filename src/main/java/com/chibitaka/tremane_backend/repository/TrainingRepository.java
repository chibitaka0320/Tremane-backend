package com.chibitaka.tremane_backend.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.response.TrainingResponseDto;
import com.chibitaka.tremane_backend.entity.TrainingEntity;
import com.chibitaka.tremane_backend.entity.TrainingRecordEntity;

@Mapper
public interface TrainingRepository {

    /** ユーザーID、日付検索 */
    List<TrainingRecordEntity> findByUserIdAndDate(TrainingEntity entity);

    /** トレーニングID検索 */
    TrainingResponseDto findById(long trainingId);

    /** トレーニング記録追加 */
    int insertTraining(TrainingEntity entity);

    /** トレーニング記録更新 */
    int update(TrainingEntity entity);
}
