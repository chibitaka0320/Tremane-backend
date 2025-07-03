package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.UserGoalEntity;

/** ユーザーゴールテーブル */
@Mapper
public interface UserGoalRepository {
    /** 取得 */
    UserGoalEntity findById(Long userId);
}
