package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

/** ユーザーエンティティ */
@Data
public class UserEntity {
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
