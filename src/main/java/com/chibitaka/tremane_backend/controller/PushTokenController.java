package com.chibitaka.tremane_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.form.PushTokenForm;
import com.chibitaka.tremane_backend.service.PushTokenService;

import lombok.RequiredArgsConstructor;

/** プッシュ通知トークン用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/push")
public class PushTokenController {

    /** プッシュトークンService */
    private final PushTokenService pushTokenService;

    /** プッシュトークン登録 */
    @PostMapping("/register")
    public ResponseEntity<Void> registerPushToken(@RequestBody PushTokenForm form) {
        String userId = UserInfo.getUserId();
        pushTokenService.saveOrUpdateToken(userId, form.getToken());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /** プッシュトークン削除 */
    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregisterPushToken() {
        String userId = UserInfo.getUserId();
        pushTokenService.deleteToken(userId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
