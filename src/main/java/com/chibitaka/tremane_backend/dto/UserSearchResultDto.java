package com.chibitaka.tremane_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** ユーザー検索結果用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResultDto {
    private String userId; // ユーザーID
    private String handle; // ID（検索用ハンドル）
    private String nickname; // ニックネーム
    private String iconUrl; // プロフィールアイコンURL
    private String status; // 友達ステータス
    private String requestId; // 友達リクエストID
}
