package com.chibitaka.tremane_backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザープロフィール */
@Data
public class UserProfileEntity {
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
