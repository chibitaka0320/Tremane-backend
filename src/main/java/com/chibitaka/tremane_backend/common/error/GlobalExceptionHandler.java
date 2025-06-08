package com.chibitaka.tremane_backend.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chibitaka.tremane_backend.common.dto.ErrorResponseDto;

/**
 * 共有エラーハンドリング
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /** APIエラー用ハンドリング */
    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<ErrorResponseDto> handleCommonException(ApiResponseException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
