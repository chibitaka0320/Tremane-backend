package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** ユーザープロフィール用DTO */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private String userId; // ユーザーID
    private Double height; // 身長
    private Double weight; // 体重
    private LocalDate birthday; // 誕生日
    private Integer gender; // 性別区分
    private Integer activeLevel; // アクティブレベル
    private LocalDateTime createdAt; // 作成日時
    private LocalDateTime updatedAt; // 更新日時
}
