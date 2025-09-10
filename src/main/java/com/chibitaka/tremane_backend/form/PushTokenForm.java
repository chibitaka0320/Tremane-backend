package com.chibitaka.tremane_backend.form;

import lombok.Data;

/** プッシュ通知トークン用Form */
@Data
public class PushTokenForm {
    private String token; // トークン
}
