package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.error.AuthenticationException;
import com.chibitaka.tremane_backend.common.util.JwtUtil;
import com.chibitaka.tremane_backend.dto.SignInDto;
import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignInForm;
import com.chibitaka.tremane_backend.repository.RefreshTokenRepository;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.chibitaka.tremane_backend.vo.EmailVo;

import lombok.RequiredArgsConstructor;

/**
 * ログイン用サービスクラス
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SignInService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;

    /** ユーザーログイン */
    public SignInDto singIn(SignInForm form) {
        SignInDto dto = new SignInDto();

        UserEntity user = userRepository.findByEmail(new EmailVo(form.getEmail()));
        if (user != null && passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            Long userId = user.getUserId();
            String accessToken = jwtUtil.createAccessToken(userId);
            String refreshToken = jwtUtil.createRefreshToken();

            RefreshTokenEntity refreshEntity = new RefreshTokenEntity(userId, refreshToken,
                    LocalDateTime.now().plusYears(1));
            refreshTokenRepository.insert(refreshEntity);

            dto.setAccessToken(accessToken);
            dto.setRefreshToken(refreshToken);

            return dto;
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), null,
                    messageSource.getMessage("10002E", null, Locale.JAPAN));
        }
    }
}
