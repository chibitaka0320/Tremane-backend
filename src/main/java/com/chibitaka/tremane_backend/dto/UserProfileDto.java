package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;

import lombok.Data;

/** ユーザープロフィール情報DTO */
@Data
public class UserProfileDto {
    private String nickname;
    private Double weight;
    private Double height;
    private LocalDate birthday;
    private Integer age;
    private Integer gender;
    private Integer activeLevel;
    private Integer bmr;
    private Integer totalCalorie;
}
