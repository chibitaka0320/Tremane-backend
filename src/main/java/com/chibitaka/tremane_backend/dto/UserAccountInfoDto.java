package com.chibitaka.tremane_backend.dto;

import lombok.Data;

@Data
public class UserAccountInfoDto {

    /** ユーザーID */
    private String userId;

    /** メールアドレス */
    private String email;

    /** ニックネーム */
    private String nickname;

    /** フレンドフラグ */
    private String status;
}
