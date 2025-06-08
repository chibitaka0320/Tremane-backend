package com.chibitaka.tremane_backend.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    /** APIエラー用ハンドリング */
    // TODO: APIエラーの粒度を分ける（認可エラー、リソース未検出、業務エラーなど）
    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<ErrorResponseDto> handleCommonException(ApiResponseException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getResponseCode()));
    }

    /** 認証エラー用ハンドリング */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthException(AuthenticationException e) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getResponseCode()));
    }

    /** その他エラー全てのハンドリング */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> handleError(Throwable e) {
        e.printStackTrace();

        ErrorResponseDto response = new ErrorResponseDto();
        response.setMessage("その他エラーが発生しました");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // データベース障害
    // 外部API通信失敗
    // ビジネスエラー（重複登録、業務ルール違反）
    // 認証・認可エラー
    // リソース未検出エラー
    // バリデーションエラー
}
