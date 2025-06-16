package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.error.AuthenticationException;
import com.chibitaka.tremane_backend.common.util.JwtUtil;
import com.chibitaka.tremane_backend.dto.RefreshDto;
import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.form.RefreshTokenForm;
import com.chibitaka.tremane_backend.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

/**
 * 認証用サービスクラス
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshDto refreshAccessToken(RefreshTokenForm form) {

        if (!jwtUtil.validateRefreshToken(form.getRefreshToken())) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.value(), "10006E", null);
        }

        Long userId = jwtUtil.extractUserId(form.getRefreshToken());
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        RefreshTokenEntity refreshEntity = new RefreshTokenEntity(userId, refreshToken, form.getDeviceInfo(),
                LocalDateTime.now().plusYears(1));

        refreshTokenRepository.update(refreshEntity);

        RefreshDto dto = new RefreshDto();
        dto.setAccessToken(accessToken);
        dto.setAccessToken(refreshToken);

        return dto;
    }
}
