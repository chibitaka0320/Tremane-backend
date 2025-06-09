package com.chibitaka.tremane_backend.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String accessToken;
    private String refreshToken;
}
