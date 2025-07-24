package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.UserProfileEntity;

/** ユーザープロフィールテーブル */
@Mapper
public interface UserProfileRepository {
    /** 取得 */
    UserProfileEntity findById(String userId, LocalDateTime updatedAt);

    /* + ユーザー追加更新 */
    int upsert(UserProfileEntity record);
}
