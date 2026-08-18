package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.ExerciseEntity;

/** トレーニング種目操作用インターフェース */
@Mapper
public interface ExerciseRepository {

    /** トレーニング種目一覧取得(システム登録) */
    List<ExerciseEntity> findBySystemUser(LocalDateTime updatedAt);

    /** マイトレーニング種目一覧取得 */
    List<ExerciseEntity> findByUserId(String ownerUserId, LocalDateTime updatedAt);

    /** マイトレーニング種目追加・更新 */
    int upsertMyExercise(ExerciseEntity entity);

    /** マイトレーニング種目削除 */
    int deleteMyExercise(String ownerUserId, String exerciseId);
}
