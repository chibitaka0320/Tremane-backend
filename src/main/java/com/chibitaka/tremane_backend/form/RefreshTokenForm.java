package com.chibitaka.tremane_backend.form;

import lombok.Data;

/**
 * アクセストークン再発行用フォームクラス
 */
@Data
public class RefreshTokenForm {
    private String refreshToken;
    private String deviceInfo;
}
