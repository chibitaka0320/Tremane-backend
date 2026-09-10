package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.EmailVerificationCodeEntity;

/** メールアドレス確認コード（OTP）テーブル操作用インターフェース */
@Mapper
public interface EmailVerificationCodeRepository {
    /** ユーザーIDで検索 */
    EmailVerificationCodeEntity findByUserId(String userId);

    /** 新規発行（既存行があれば上書き） */
    int upsert(EmailVerificationCodeEntity entity);

    /** 試行回数のインクリメント */
    int incrementAttemptCount(String userId);

    /** 削除（認証成功時） */
    int deleteByUserId(String userId);
}
