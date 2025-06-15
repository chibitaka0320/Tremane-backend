package com.chibitaka.tremane_backend.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String accessToken;
    private String refreshToken;
}
