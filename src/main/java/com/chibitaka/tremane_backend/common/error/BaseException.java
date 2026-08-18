package com.chibitaka.tremane_backend.common.error;

import lombok.Getter;

/**
 * ベースエクセプションクラス
 */
@Getter
public class BaseException extends RuntimeException {
    private Integer responseCode;
    private String errorCode;

    public BaseException(Integer responseCode, String errorCode, String message) {
        super(message);
        this.responseCode = responseCode;
        this.errorCode = errorCode;
    }

}
