package com.chibitaka.tremane_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.form.PasswordResetForm;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.AuthService;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;

/** 認証用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService; // 認証Service

    /** ユーザー新規登録 */
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpForm form) {
        authService.signUp(form);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** 再認証トークン発行 */
    @PostMapping("/reauth-token")
    public ResponseEntity<String> issueReauthToken() throws Exception {
        String userId = UserInfo.getUserId();
        String customToken = authService.issueReauthToken(userId);
        return ResponseEntity.status(HttpStatus.OK).body(customToken);
    }

    /** メールアドレス確認メール送信 */
    @PostMapping("/send-verification-email")
    public ResponseEntity<Void> sendVerificationEmail() throws FirebaseAuthException {
        String userId = UserInfo.getUserId();
        authService.sendVerificationEmail(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** パスワード再設定メール送信 */
    @PostMapping("/send-password-reset-email")
    public ResponseEntity<Void> sendPasswordResetEmail(@RequestBody PasswordResetForm form) throws FirebaseAuthException {
        authService.sendPasswordResetEmail(form.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
