package com.chibitaka.tremane_backend.common.error;

/**
 * 認証用エクセプションクラス
 */
public class AuthenticationException extends BaseException {

    public AuthenticationException(Integer responseCode, String errorCode, String message) {
        super(responseCode, errorCode, message);
    }

}
