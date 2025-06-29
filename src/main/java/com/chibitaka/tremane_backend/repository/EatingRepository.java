package com.chibitaka.tremane_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.EatingEntity;

/** eatingsテーブル管理用リポジトリ */
@Mapper
public interface EatingRepository {
    /** 食事記録取得 */
    List<EatingEntity> findByUserIdAndDate(Long userId, LocalDate date);

    /** 食事記録追加 */
    int insertEating(EatingEntity entity);
}
