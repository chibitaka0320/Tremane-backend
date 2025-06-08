package com.chibitaka.tremane_backend.common.error;

import lombok.Getter;

/**
 * ベースエクセプションクラス
 */
@Getter
public class BaseException extends RuntimeException {
    private int responseCode;
    private String errorCode;

    public BaseException(int responseCode, String errorCode, String message) {
        super(message);
        this.responseCode = responseCode;
        this.errorCode = errorCode;
    }

}
