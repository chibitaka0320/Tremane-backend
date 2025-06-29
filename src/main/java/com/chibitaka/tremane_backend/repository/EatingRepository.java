package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.EatingEntity;

/** eatingsテーブル管理用リポジトリ */
@Mapper
public interface EatingRepository {
    /** 食事記録追加 */
    int insertEating(EatingEntity entity);
}
