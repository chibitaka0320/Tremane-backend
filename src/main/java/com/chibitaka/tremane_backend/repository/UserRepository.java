package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.vo.EmailVo;

/** ユーザーテーブル操作りポジトリ */
@Mapper
public interface UserRepository {

        /** ユーザーのID検索 */
        @Select({
                        "SELECT user_id, name, email, password, created_at, updated_at",
                        "FROM users WHERE user_id = #{userId}"
        })
        UserEntity findById(Long id);

        /** ユーザーのメールアドレス検索 */
        @Select({
                        "SELECT user_id, name, email, password, created_at, updated_at",
                        "FROM users WHERE email = #{email}"
        })
        UserEntity findByEmail(EmailVo email);

        /** ユーザー登録 */
        @Insert({
                        "INSERT INTO users(email, password)",
                        "VALUES(#{email}, #{password})"
        })
        @Options(useGeneratedKeys = true, keyProperty = "userId")
        int insert(UserEntity record);
}
