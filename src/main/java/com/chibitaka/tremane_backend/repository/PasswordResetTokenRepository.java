package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.PasswordResetTokenEntity;

/** パスワード再設定トークンテーブル操作用インターフェース */
@Mapper
public interface PasswordResetTokenRepository {
    /** ユーザーIDで検索 */
    PasswordResetTokenEntity findByUserId(String userId);

    /** トークンで検索 */
    PasswordResetTokenEntity findByToken(String token);

    /** 新規発行（既存行があれば上書き） */
    int upsert(PasswordResetTokenEntity entity);

    /** 削除（再設定成功時） */
    int deleteByUserId(String userId);
}
