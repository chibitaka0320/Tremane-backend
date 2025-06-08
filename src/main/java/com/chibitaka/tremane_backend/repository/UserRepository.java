package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.chibitaka.tremane_backend.entity.UserEntity;

/** ユーザーテーブル操作りポジトリ */
@Mapper
public interface UserRepository {

    /** ユーザー登録 */
    @Insert({
            "INSERT INTO users(email, password)",
            "VALUES(#{email}, #{password})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(UserEntity record);
}
