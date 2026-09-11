package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** パスワード再設定（トークン検証・更新）用Form */
@Data
public class ResetPasswordForm {
    private String token; // 再設定トークン
    private String newPassword; // 新しいパスワード
}
