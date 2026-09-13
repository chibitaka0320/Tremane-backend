package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** ユーザーアイコン更新用Form */
@Data
public class UserIconForm {
    private String iconUrl; // プロフィールアイコンURL（nullで削除）
}
