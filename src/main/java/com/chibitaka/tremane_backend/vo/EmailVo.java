package com.chibitaka.tremane_backend.vo;

import lombok.Value;

/** Email ValueObject */
@Value
public class EmailVo {
    private String value;

    public EmailVo(String value) {
        if (value == null || !value.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("メールアドレス形式が不正です");
        }
        this.value = value;
    }
}
