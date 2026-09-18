package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.EatingEntity;

/** 食事テーブル操作用インターフェース */
@Mapper
public interface EatingRepository {

    /** 食事詳細取得(ID、所有者チェックあり) */
    EatingEntity findById(String eatingId, String userId);

    /** ユーザー食事一覧取得(更新情報) */
    List<EatingEntity> findByUserId(String userId, LocalDateTime updatedAt);

    /** 食事追加更新 */
    int upsert(EatingEntity entity);

    /** 食事記録削除 */
    int delete(String userId, String eatingId);

    /** 食事記録IDに紐づく食品を一括削除（食事記録削除時のカスケード用） */
    int deleteByMealId(String userId, String mealId);

}
