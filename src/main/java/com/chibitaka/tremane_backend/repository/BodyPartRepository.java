package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.BodyPartDto;
import com.chibitaka.tremane_backend.entity.BodyPartEntity;

@Mapper
public interface BodyPartRepository {

    /** 部位一覧取得 */
    List<BodyPartEntity> getBodyPartExerciseList();

    /** 部位更新情報取得 */
    List<BodyPartDto> getBodyPart(LocalDateTime updatedAt);
}
