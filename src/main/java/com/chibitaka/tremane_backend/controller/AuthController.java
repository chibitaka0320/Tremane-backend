package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.SignUpService;

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
}
