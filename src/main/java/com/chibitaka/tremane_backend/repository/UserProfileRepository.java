package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.UserProfileEntity;

/** ユーザープロフィールテーブル */
@Mapper
public interface UserProfileRepository {
    /** 取得 */
    UserProfileEntity findById(Long userId);

    /* + ユーザー追加更新 */
    int upsert(UserProfileEntity record);
}
