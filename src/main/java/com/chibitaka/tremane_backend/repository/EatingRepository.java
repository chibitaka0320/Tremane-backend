package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.EatingEntity;

/** 食事テーブル操作用インターフェース */
@Mapper
public interface EatingRepository {

    /** 食事詳細取得(ID) */
    EatingEntity findById(String eatingId);

    /** ユーザー食事一覧取得(更新情報) */
    List<EatingEntity> findByUserId(String userId, LocalDateTime updatedAt);

    /** 食事追加更新 */
    int upsert(EatingEntity entity);

    /** 食事記録削除 */
    int delete(String userId, String eatingId);

}
