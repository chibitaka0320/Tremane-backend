package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.ExerciseDto;
import com.chibitaka.tremane_backend.entity.MyExerciseEntity;

@Mapper
public interface ExerciseRepository {

    List<ExerciseDto> getExercise(LocalDateTime updatedAt);

    List<ExerciseDto> getMyExercise(String userId, LocalDateTime updatedAt);

    /** マイ種目追加・更新 */
    int upsertMyExercise(MyExerciseEntity entity);

    /** マイ種目削除 */
    int deleteMyExercise(String userId, String exerciseId);
}
