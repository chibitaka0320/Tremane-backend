package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 通知テーブル用Entity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {
    private String notificationId; // 通知ID
    private String userId; // ユーザーID

    // TODO: ENUMにする
    private String type; // 通知タイプ

    private String relatedId; // リクエストID
    private String message; // 通知メッセージ
    private boolean isRead; // 既読・未読
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
