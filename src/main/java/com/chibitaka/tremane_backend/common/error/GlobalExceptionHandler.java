package com.chibitaka.tremane_backend.common.error;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chibitaka.tremane_backend.common.dto.ErrorResponseDto;

/**
 * 共有エラーハンドリング
 */
// TODO: ログ出力処理の作成
// TODO: エラーコードの体系化
// TODO: バリデーションエラー
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /** APIエラー用ハンドリング */
    // TODO: APIエラーの粒度を分ける（認可エラー、リソース未検出、業務エラーなど）
    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<ErrorResponseDto> handleCommonException(ApiResponseException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getResponseCode()));
    }

    /** バリデーションエラー用ハンドリング */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    /** 認証エラー用ハンドリング */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthException(AuthenticationException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getResponseCode()));
    }

    /** 不正引数時のエラーハンドリング */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalException(IllegalArgumentException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode(e.getMessage());
        response.setMessage(messageSource.getMessage(e.getMessage(), null, Locale.JAPAN));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** その他エラー全てのハンドリング */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> handleError(Throwable e) {
        e.printStackTrace();

        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode("10001E");
        response.setMessage(messageSource.getMessage("10001E", null, Locale.JAPAN));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // データベース障害
    // 外部API通信失敗
    // ビジネスエラー（重複登録、業務ルール違反）
    // 認証・認可エラー
    // リソース未検出エラー
    // バリデーションエラー
}
