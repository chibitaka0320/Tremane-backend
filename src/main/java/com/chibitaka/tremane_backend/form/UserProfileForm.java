package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザープロフィール追加更新用Form */
@Data
public class UserProfileForm {
    private String userId; // ユーザーID
    private Double height; // 身長
    private Double weight; // 体重
    private LocalDate birthday; // 生年月日
    private Integer gender; // 性別区分
    private Integer activeLevel; // アクティブレベル
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
