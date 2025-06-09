package com.chibitaka.tremane_backend.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.common.util.JwtUtil;
import com.chibitaka.tremane_backend.domain.UserDomain;
import com.chibitaka.tremane_backend.dto.SignUpDto;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.chibitaka.tremane_backend.vo.EmailVo;

import lombok.RequiredArgsConstructor;

/**
 * 認証系サービスクラス
 */
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserDomain userDomain;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;

    /** ユーザー新規登録 */
    public SignUpDto signUp(SignUpForm form) {

        UserEntity user = new UserEntity();
        SignUpDto dto = new SignUpDto();

        user.setEmail(new EmailVo(form.getEmail()));
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        if (!userDomain.exists(user.getEmail())) {
            userRepository.insert(user);

            Long userId = user.getUserId();
            String token = jwtUtil.createJwtToken(userId);
            dto.setAccessToken(token);

            return dto;
        } else {
            throw new ApiResponseException(HttpStatus.CONFLICT.value(), null,
                    messageSource.getMessage("10002E", null, Locale.JAPAN));
        }
    }
}
