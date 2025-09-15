package com.chibitaka.tremane_backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 通知用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String notificationId; // 通知ID
    private String userId; // ユーザーID
    private String notificationSource; // 通知元
    private String type; // 通知種類
    private String relatedId; // 関連リソースID
    private String message; // 表示メッセージ
    private boolean isRead; // 既読フラグ
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時

    // 友達申請通知用
    private String status; // 友達申請ステータス
}
