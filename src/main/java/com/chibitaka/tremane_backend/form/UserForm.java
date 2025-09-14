package com.chibitaka.tremane_backend.form;

import java.time.LocalDateTime;

import lombok.Data;

/** ユーザー更新用Form */
@Data
public class UserForm {
    private String nickname; // ニックネーム
    private LocalDateTime updatedAt; // 更新日時
}
