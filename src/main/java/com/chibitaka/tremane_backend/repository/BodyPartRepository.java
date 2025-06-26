package com.chibitaka.tremane_backend.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.BodyPartEntity;

@Mapper
public interface BodyPartRepository {

    /** 部位一覧取得 */
    List<BodyPartEntity> getBodyPartExerciseList();
}
