package com.chibitaka.tremane_backend.form;

import java.time.LocalDate;

import lombok.Data;

/** ユーザープロフィール情報更新form */
@Data
public class UserProfileForm {
    private String nickname;
    private Double weight;
    private Double height;
    private LocalDate birthday;
    private Integer gender;
    private Integer activeLevel;
}
