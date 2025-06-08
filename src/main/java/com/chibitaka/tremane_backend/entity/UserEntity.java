package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

/** ユーザーエンティティ */
@Data
public class UserEntity {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
