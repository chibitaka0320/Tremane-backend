package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.repository.UserPushTokenRepository;

import lombok.RequiredArgsConstructor;

/** プッシュ通知トークン用Service */
@Service
@RequiredArgsConstructor
@Transactional
public class PushTokenService {

    /** ユーザープッシュ通知トークンRepository */
    private final UserPushTokenRepository pushTokenRepository;

    /** トークンの追加 or 更新 */
    public void saveOrUpdateToken(String userId, String token) {
        pushTokenRepository.saveOrUpdate(userId, token);
    }

}
