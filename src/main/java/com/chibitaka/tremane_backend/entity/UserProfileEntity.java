package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/** ユーザープロフィール */
@Data
public class UserProfileEntity {
    private Long userId;
    private String nickname;
    private Double height;
    private Double weight;
    private Date birthday;
    private Integer gender;
    private Integer activeLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
