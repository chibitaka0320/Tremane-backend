package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.BodyPartEntity;

/** トレーニング部位操作用インターフェース */
@Mapper
public interface BodyPartRepository {

    /** 部位更新情報一覧取得 */
    List<BodyPartEntity> findAll(LocalDateTime updatedAt);
}
