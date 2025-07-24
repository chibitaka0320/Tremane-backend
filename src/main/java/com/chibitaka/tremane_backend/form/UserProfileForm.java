package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザープロフィール情報更新form */
@Data
public class UserProfileForm {
    private String userId;
    private String nickname;
    private Double height;
    private Double weight;
    private LocalDate birthday;
    private Integer gender;
    private Integer activeLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
