package com.chibitaka.tremane_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** ユーザープロフィール情報DTO */
@Data
public class UserProfileDto {
    private String userId;
    private Double height;
    private Double weight;
    private LocalDate birthday;
    private Integer gender;
    private Integer activeLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
