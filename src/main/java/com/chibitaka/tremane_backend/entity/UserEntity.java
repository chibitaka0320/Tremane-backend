package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** ユーザーテーブルEntity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String userId; // ユーザーID
    private String nickname; // ニックネーム
    private String handle; // 検索用ID（ハンドル）
    private LocalDateTime handleUpdatedAt; // ID最終変更日時
    private LocalDateTime createdAt; // 作成日
    private LocalDateTime updatedAt; // 更新日
}
