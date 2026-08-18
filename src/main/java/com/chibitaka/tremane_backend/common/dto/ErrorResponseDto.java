package com.chibitaka.tremane_backend.common.dto;

import lombok.Data;

/** エラーレスポンス用DTO */
@Data
public class ErrorResponseDto {
    private String code;
    private String message;
}
