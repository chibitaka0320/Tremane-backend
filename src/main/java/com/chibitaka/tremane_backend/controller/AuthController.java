package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.dto.SignUpDto;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.SignUpService;

import lombok.RequiredArgsConstructor;

/**
 * 認証系コントローラー
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SignUpService signUpService;

    /**
     * ユーザー新規登録
     * 
     * @throws Exception
     */
    @PostMapping("/auth/signUp")
    public ResponseEntity<SignUpDto> signUp(@Validated @RequestBody SignUpForm form) {
        SignUpDto dto = signUpService.signUp(form);
        return ResponseEntity.ok(dto);
    }
}
