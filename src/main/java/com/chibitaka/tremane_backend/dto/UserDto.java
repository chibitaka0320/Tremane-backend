package com.chibitaka.tremane_backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** ユーザー用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId; // ユーザーID
    private String nickname; // ニックネーム
    private String handle; // ID（検索用ハンドル）
    private String iconUrl; // プロフィールアイコンURL
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
