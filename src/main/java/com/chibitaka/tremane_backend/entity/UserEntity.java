package com.chibitaka.tremane_backend.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.chibitaka.tremane_backend.vo.EmailVo;

import lombok.Data;

/** ユーザーエンティティ */
@Data
public class UserEntity {
    private Long userId;
    private EmailVo email;
    private String password;
    private String nickname;
    private Integer height;
    private Integer weight;
    private Date birthday;
    private Integer gender;
    private Integer activeLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
