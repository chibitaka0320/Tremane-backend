package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** 新規登録用Form */
@Data
public class SignUpForm {
    private String userId; // ユーザーID
    private String nickname; // ニックネーム
}
