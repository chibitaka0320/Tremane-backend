package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.UserEntity;

/** ユーザーテーブル操作用インターフェース */
@Mapper
public interface UserRepository {

        /** ユーザーのID検索 */
        UserEntity findById(Long id);

        /** ユーザー登録 */
        int insert(UserEntity record);

}
