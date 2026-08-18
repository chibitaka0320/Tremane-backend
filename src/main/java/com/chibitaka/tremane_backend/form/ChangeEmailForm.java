package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** メールアドレス変更用Form */
@Data
public class ChangeEmailForm {
    private String newEmail; // 新しいメールアドレス
}
