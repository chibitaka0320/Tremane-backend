package com.chibitaka.tremane_backend.common.error;

import lombok.Getter;

/** APIエラー時エクセプション */
@Getter
public class ApiResponseException extends RuntimeException {
    private int responseCode;
    private String errorCode;

    public ApiResponseException(int responseCode, String errorCode, String message) {
        super(message);
        this.responseCode = responseCode;
        this.errorCode = errorCode;
    }
}
