package com.chibitaka.tremane_backend.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.EatingDto;
import com.chibitaka.tremane_backend.entity.EatingEntity;

/** eatingsテーブル管理用リポジトリ */
@Mapper
public interface EatingRepository {

    /** 食事記録更新情報取得 */
    List<EatingDto> getEatings(String userId, LocalDateTime updatedAt);

    /** 食事記録取得 */
    List<EatingEntity> findByUserIdAndDate(String userId, LocalDate date);

    /** 食事記録詳細取得 */
    EatingDto findById(String eatingId);

    /** 食事記録追加 */
    int upsertEating(EatingEntity entity);

    /** 食事記録更新 */
    int update(EatingEntity entity);

    /** 食事記録削除 */
    int delete(String userId, String eatingId);

}
