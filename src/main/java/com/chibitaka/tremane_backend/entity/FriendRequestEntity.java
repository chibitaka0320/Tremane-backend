package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

/** 友達リクエストテーブル用 Entity */
@Data
public class FriendRequestEntity {

    /** リクエストID */
    private String requestId;

    /** リクエストユーザー */
    private String requestUserId;

    /** レシーブユーザー */
    private String receiveUserId;

    /** ステータス */
    private String status;

    /** 作成日 */
    private LocalDateTime createdAt;

    /** 更新日 */
    private LocalDateTime updatedAt;
}
