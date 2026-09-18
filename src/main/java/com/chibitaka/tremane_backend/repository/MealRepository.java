package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.MealEntity;

/** 食事記録テーブル操作用インターフェース */
@Mapper
public interface MealRepository {

    /** 食事記録詳細取得(ID、所有者チェックあり) */
    MealEntity findById(String mealId, String userId);

    /** ユーザー食事記録一覧取得(更新情報) */
    List<MealEntity> findByUserId(String userId, LocalDateTime updatedAt);

    /** 食事記録追加更新 */
    int upsert(MealEntity entity);

    /** 食事記録削除 */
    int delete(String userId, String mealId);

}
