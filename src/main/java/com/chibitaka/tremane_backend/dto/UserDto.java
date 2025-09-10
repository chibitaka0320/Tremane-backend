package com.chibitaka.tremane_backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
