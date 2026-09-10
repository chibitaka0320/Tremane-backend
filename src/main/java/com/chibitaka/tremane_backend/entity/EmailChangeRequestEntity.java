package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** メールアドレス変更リクエスト（OTP）テーブルEntity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailChangeRequestEntity {
    private String userId; // ユーザーID
    private String newEmail; // 変更後のメールアドレス
    private String code; // 確認コード（6桁）
    private LocalDateTime expiresAt; // 有効期限
    private Integer attemptCount; // 試行回数
    private LocalDateTime createdAt; // 発行日時
}
