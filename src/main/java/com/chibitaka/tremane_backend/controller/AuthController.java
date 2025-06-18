package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.dto.RefreshDto;
import com.chibitaka.tremane_backend.dto.SignInDto;
import com.chibitaka.tremane_backend.dto.SignUpDto;
import com.chibitaka.tremane_backend.form.RefreshTokenForm;
import com.chibitaka.tremane_backend.form.SignInForm;
import com.chibitaka.tremane_backend.form.SignOutForm;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.AuthService;
import com.chibitaka.tremane_backend.service.SignInService;
import com.chibitaka.tremane_backend.service.SignOutService;
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
    private final SignInService signInService;
    private final SignOutService signOutService;
    private final AuthService authService;

    /**
     * ユーザー新規登録
     */
    @PostMapping("/signUp")
    public ResponseEntity<SignUpDto> signUp(@Validated @RequestBody SignUpForm form) {
        SignUpDto dto = signUpService.signUp(form);
        return ResponseEntity.ok(dto);
    }

    /**
     * ユーザーログイン
     */
    @PostMapping("/signIn")
    public ResponseEntity<SignInDto> signIn(@Validated @RequestBody SignInForm form) {
        SignInDto dto = signInService.singIn(form);
        return ResponseEntity.ok(dto);
    }

    /**
     * ユーザーログアウト
     */
    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@Validated @RequestBody SignOutForm form) {
        signOutService.signOut(form);
        return ResponseEntity.ok().build();
    }

    /**
     * アクセストークン再発行
     */
    @PostMapping("/refresh")
    public ResponseEntity<RefreshDto> refreshAccessToken(@RequestBody RefreshTokenForm form) {
        RefreshDto dto = authService.refreshAccessToken(form);
        return ResponseEntity.ok(dto);
    }

}
