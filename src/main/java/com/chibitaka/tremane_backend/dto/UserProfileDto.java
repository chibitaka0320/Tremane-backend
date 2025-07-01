package com.chibitaka.tremane_backend.dto;

import java.util.Date;

import lombok.Data;

/** ユーザープロフィール情報DTO */
@Data
public class UserProfileDto {
    private String nickname;
    private Double weight;
    private Double height;
    private Date birthday;
    private Integer gender;
    private Integer activeLevel;
}
