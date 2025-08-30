package com.chibitaka.tremane_backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.SignUpService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import lombok.RequiredArgsConstructor;

/**
 * 認証系コントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final SignUpService signUpService;

    /**
     * ユーザー新規登録
     */
    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Validated @RequestBody SignUpForm form) {
        signUpService.signUp(form);
        return ResponseEntity.status(204).build();
    }

    /** 匿名ユーザーカスタムトークン発行 */
    @PostMapping("/reauth_token")
    public Map<String, String> issueReauthToken(@RequestHeader("Authorization") String bearer) throws Exception {
        String idToken = bearer.replace("Bearer ", "");

        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decoded.getUid();
        String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        return Map.of("customToken", customToken);
    }
}
