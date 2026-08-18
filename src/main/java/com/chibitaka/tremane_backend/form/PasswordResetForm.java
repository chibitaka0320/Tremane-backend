package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** パスワード再設定用Form */
@Data
public class PasswordResetForm {
    private String email; // メールアドレス
}
