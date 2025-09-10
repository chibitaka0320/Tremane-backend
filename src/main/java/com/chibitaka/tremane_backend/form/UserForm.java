package com.chibitaka.tremane_backend.form;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserForm {
    private String nickname;
    private LocalDateTime updatedAt;
}
