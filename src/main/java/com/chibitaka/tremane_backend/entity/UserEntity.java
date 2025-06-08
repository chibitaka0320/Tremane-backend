package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;

import com.chibitaka.tremane_backend.vo.EmailVo;

import lombok.Data;

/** ユーザーエンティティ */
@Data
public class UserEntity {
    private Long userId;
    private String name;
    private EmailVo email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
