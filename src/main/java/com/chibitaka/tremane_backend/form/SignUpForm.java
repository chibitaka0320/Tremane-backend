package com.chibitaka.tremane_backend.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新規登録用フォームクラス
 */
@Data
public class SignUpForm {

    @Email
    private String email;

    @NotNull
    private String password;

    private String deviceInfo;
}
