package com.chibitaka.tremane_backend.vo;

import lombok.Value;

/** Email ValueObject */
@Value
public class EmailVo {

    private String value;

    public EmailVo(String value) {
        if (value == null || !value.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("10003E");
        }
        this.value = value;
    }
}
