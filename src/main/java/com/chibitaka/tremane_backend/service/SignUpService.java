package com.chibitaka.tremane_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chibitaka.tremane_backend.common.util.JwtUtil;
import com.chibitaka.tremane_backend.dto.SignUpDto;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 認証系サービスクラス
 */
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /** ユーザー新規登録 */
    public SignUpDto signUp(SignUpForm form) {

        UserEntity user = new UserEntity();
        SignUpDto dto = new SignUpDto();

        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        int result = userRepository.insert(user);

        if (result == 1) {
            Long userId = user.getUserId();
            String token = jwtUtil.createJwtToken(userId);
            dto.setAccessToken(token);
        }
        return dto;
    }
}
