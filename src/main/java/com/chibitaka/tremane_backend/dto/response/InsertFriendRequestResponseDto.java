package com.chibitaka.tremane_backend.dto.response;

import lombok.Data;

/** 友達申請追加処理結果DTO */
@Data
public class InsertFriendRequestResponseDto {
    /** リクエストID */
    private String requestId;
    /** 結果ステータス(success, conflict, receive) */
    private String status;
}
