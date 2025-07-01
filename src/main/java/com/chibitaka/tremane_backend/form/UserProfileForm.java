package com.chibitaka.tremane_backend.form;

import java.util.Date;

import lombok.Data;

/** ユーザープロフィール情報更新form */
@Data
public class UserProfileForm {
    private String nickname;
    private Double weight;
    private Double height;
    private Date birthday;
    private Integer gender;
    private Integer activeLevel;
}
