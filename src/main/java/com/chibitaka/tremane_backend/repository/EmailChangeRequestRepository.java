package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.EmailChangeRequestEntity;

/** メールアドレス変更リクエスト（OTP）テーブル操作用インターフェース */
@Mapper
public interface EmailChangeRequestRepository {
    /** ユーザーIDで検索 */
    EmailChangeRequestEntity findByUserId(String userId);

    /** 新規発行（既存行があれば上書き） */
    int upsert(EmailChangeRequestEntity entity);

    /** 試行回数のインクリメント */
    int incrementAttemptCount(String userId);

    /** 削除（認証成功時） */
    int deleteByUserId(String userId);
}
