package com.chibitaka.tremane_backend.common.error;

import lombok.Getter;

/** APIエラー時エクセプション */
@Getter
public class ApiResponseException extends RuntimeException {
    private String errorCode;

    public ApiResponseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
