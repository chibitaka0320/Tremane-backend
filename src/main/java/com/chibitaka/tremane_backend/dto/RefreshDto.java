package com.chibitaka.tremane_backend.dto;

import lombok.Data;

/** アクセストークン再発行用DTO */
@Data
public class RefreshDto {
    private String accessToken;
    private String refreshToken;
}
