package com.chibitaka.tremane_backend.repository;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.vo.EmailVo;

/** ユーザーテーブル操作用インターフェース */
public interface UserRepository {

        /** ユーザーのID検索 */
        UserEntity findById(Long id);

        /** ユーザーのメールアドレス検索 */
        UserEntity findByEmail(EmailVo email);

        /** ユーザー登録 */
        int insert(UserEntity record);

}
