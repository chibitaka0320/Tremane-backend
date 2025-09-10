package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.UserDto;
import com.chibitaka.tremane_backend.entity.UserEntity;

/** ユーザーテーブル操作用インターフェース */
@Mapper
public interface UserRepository {

        /** ユーザーのID検索 */
        UserDto findById(String id);

        /** ユーザー登録 */
        int insert(UserEntity record);

        /** 削除 */
        int delete(String userId);

        /** ユーザー情報更新 */
        int update(UserEntity entity);

}
