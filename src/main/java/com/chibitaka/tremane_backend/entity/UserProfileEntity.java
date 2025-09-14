package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** ユーザープロフィールテーブルEntity */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileEntity {
    private String userId; // ユーザーID
    private Double height; // 身長
    private Double weight; // 体重
    private LocalDate birthday; // 日付
    private Integer gender; // 性別区分
    private Integer activeLevel; // アクティブレベル
    private LocalDateTime createdAt; // 作成日
    private LocalDateTime updatedAt; // 更新日
}
