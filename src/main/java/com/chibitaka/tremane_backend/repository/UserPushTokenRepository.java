package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

/** プッシュ通知トークンテーブル操作用インターフェース */
@Mapper
public interface UserPushTokenRepository {

    /** プッシュ通知トークン追加更新 */
    void saveOrUpdate(String userId, String token);

    /** プッシュ通知トークン取得 */
    String findTokenByUserId(String userId);

    /** プッシュ通知トークン削除 */
    void delete(String userId);
}
