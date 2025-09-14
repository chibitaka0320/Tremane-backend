package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.UserEntity;

/** ユーザーテーブル操作用インターフェース */
@Mapper
public interface UserRepository {
        /** ユーザーのID検索 */
        UserEntity findById(String userId);

        /** ユーザー登録 */
        int insert(UserEntity user);

        /** 削除 */
        int deleteById(String userId);

        /** ユーザー情報更新 */
        int update(UserEntity user);
}
