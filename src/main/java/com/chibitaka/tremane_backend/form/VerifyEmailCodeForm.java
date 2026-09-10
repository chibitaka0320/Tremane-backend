package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** メールアドレス確認コード検証用Form */
@Data
public class VerifyEmailCodeForm {
    private String code; // 確認コード（6桁）
}
