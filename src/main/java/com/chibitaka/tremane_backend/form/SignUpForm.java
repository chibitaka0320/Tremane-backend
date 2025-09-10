package com.chibitaka.tremane_backend.form;

import lombok.Data;

/**
 * 新規登録用フォームクラス
 */
@Data
public class SignUpForm {
    private String userId;
    private String nickname;
}
