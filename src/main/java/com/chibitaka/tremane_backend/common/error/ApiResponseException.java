package com.chibitaka.tremane_backend.common.error;

/**
 * APIエラー時エクセプション
 */
public class ApiResponseException extends BaseException {

    public ApiResponseException(int responseCode, String errorCode, String message) {
        super(responseCode, errorCode, message);
    }
}
