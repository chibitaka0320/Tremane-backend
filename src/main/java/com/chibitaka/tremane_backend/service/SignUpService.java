package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 新規登録サービスクラス
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SignUpService {

    private final UserRepository userRepository;

    /** ユーザー新規登録 */
    public void signUp(SignUpForm form) {
        UserEntity user = new UserEntity();
        user.setUserId(form.getUserId());
        user.setNickname(form.getNickname());
        userRepository.insert(user);
    }
}
