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
    private String email; // メールアドレス
    private String nickname; // ニックネーム
    private String status; // 友達ステータス
    private String requestId; // 友達リクエストID
}
