package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** メールアドレス変更コード検証用Form */
@Data
public class VerifyEmailChangeCodeForm {
    private String code; // 確認コード（6桁）
}
