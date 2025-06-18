package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** ログアウト用フォームクラス */
@Data
public class SignOutForm {
    private String accessToken;
    private String deviceInfo;
}
