package com.chibitaka.tremane_backend.dto.response;

import lombok.Data;

/** EXPOプッシュ通知レスポンス用DTO */
@Data
public class ExpoPushResponse {
    private ResponseData data;

    @Data
    public static class ResponseData {
        private String status; // ok または error
        private String id; // 成功時
        private String message; // 失敗時
    }
}
