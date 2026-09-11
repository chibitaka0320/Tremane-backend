package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.UserEntity;

/** ユーザーテーブル操作用インターフェース */
@Mapper
public interface UserRepository {
        /** ユーザーのID検索 */
        UserEntity findById(String userId);

        /** ハンドル（検索用ID）検索（大文字小文字区別なし） */
        UserEntity findByHandle(String handle);

        /** ユーザー登録 */
        int insert(UserEntity user);

        /** 削除 */
        int deleteById(String userId);

        /** ユーザー情報更新 */
        int update(UserEntity user);

        /** ハンドル（検索用ID）更新 */
        int updateHandle(UserEntity user);

        /** アイコン更新 */
        int updateIcon(UserEntity user);
}
