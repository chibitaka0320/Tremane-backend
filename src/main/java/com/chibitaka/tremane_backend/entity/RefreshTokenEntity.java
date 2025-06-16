package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/** リフレッシュトークンエンティティ */
@Data
@NoArgsConstructor
public class RefreshTokenEntity {
    private Long refreshId;
    private Long userId;
    private String token;
    private String deviceInfo;
    private LocalDateTime expiryDate;
    private boolean revoked;

    public RefreshTokenEntity(Long userId, String token, String deviceInfo, LocalDateTime expiryDate) {
        this.userId = userId;
        this.token = token;
        this.deviceInfo = deviceInfo;
        this.expiryDate = expiryDate;
    }
}
