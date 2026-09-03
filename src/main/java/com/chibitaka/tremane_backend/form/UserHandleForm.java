package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** ユーザーID（検索用ハンドル）更新用Form */
@Data
public class UserHandleForm {
    private String handle; // ID（検索用ハンドル）
}
