package com.chibitaka.tremane_backend.repository;

import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;

/** リフレッシュトークン管理テーブル操作用インターフェース */
public interface RefreshTokenRepository {

    /** リフレッシュトークン登録 */
    int insert(RefreshTokenEntity entity);

    /** リフレッシュトークン更新 */
    int update(RefreshTokenEntity entity);

    /** リフレッシュトークン削除 */
    int delete(RefreshTokenEntity entity);
}
