package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.repository.UserPushTokenRepository;

import lombok.RequiredArgsConstructor;

/** プッシュ通知トークン関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class PushTokenService {

    private final UserPushTokenRepository pushTokenRepository; // プッシュ通知トークンRepository

    /** トークン追加・更新 */
    public void saveOrUpdateToken(String userId, String token) {
        pushTokenRepository.saveOrUpdate(userId, token);
    }

    /** トークン削除 */
    public void deleteToken(String userId) {
        pushTokenRepository.delete(userId);
    }

}
