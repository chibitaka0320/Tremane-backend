package com.chibitaka.tremane_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.AuthService;

import lombok.RequiredArgsConstructor;

/** 認証用コントローラー */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService; // 認証Service

    /** ユーザー新規登録 */
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpForm form) {
        authService.signUp(form);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /** 再認証トークン発行 */
    @PostMapping("/reauth-token")
    public ResponseEntity<String> issueReauthToken() throws Exception {
        String userId = UserInfo.getUserId();
        String customToken = authService.issueReauthToken(userId);
        return ResponseEntity.status(HttpStatus.OK).body(customToken);
    }
}
