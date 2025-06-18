package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.util.JwtUtil;
import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.form.SignOutForm;
import com.chibitaka.tremane_backend.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

/**
 * ログアウト用サービスクラス
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SignOutService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public void signOut(SignOutForm form) {
        Long userId = jwtUtil.extractUserId(form.getAccessToken());

        RefreshTokenEntity entity = new RefreshTokenEntity(userId, null, form.getDeviceInfo(), null);
        refreshTokenRepository.delete(entity);
    }
}
