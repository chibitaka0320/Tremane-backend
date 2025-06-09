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
    private LocalDateTime expiryDate;
    private boolean revoked;

    public RefreshTokenEntity(Long userId, String token, LocalDateTime expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
    }
}
