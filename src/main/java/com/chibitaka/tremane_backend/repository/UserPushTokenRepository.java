package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

/** プッシュ通知トークンテーブル用Repository */
@Mapper
public interface UserPushTokenRepository {

    /** 追加 or 更新 */
    void saveOrUpdate(String userId, String token);

    /** プッシュ通知トークン取得 */
    String findTokenByUserId(String userId);
}
