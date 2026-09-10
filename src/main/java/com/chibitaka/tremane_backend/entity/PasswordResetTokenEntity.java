package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** パスワード再設定トークンテーブルEntity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetTokenEntity {
    private String userId; // ユーザーID
    private String token; // 再設定トークン
    private LocalDateTime expiresAt; // 有効期限
    private LocalDateTime createdAt; // 発行日時
}
