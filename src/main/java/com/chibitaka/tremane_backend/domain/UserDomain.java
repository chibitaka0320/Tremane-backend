package com.chibitaka.tremane_backend.domain;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.chibitaka.tremane_backend.vo.EmailVo;

import lombok.RequiredArgsConstructor;

/** ユーザーのドメインサービスクラス */
@Service
@RequiredArgsConstructor
public class UserDomain {

    private final UserRepository userRepository;

    /** ユーザー重複チェック */
    public boolean exists(EmailVo email) {
        UserEntity user = userRepository.findByEmail(email);

        if (Objects.isNull(user)) {
            return false;
        } else {
            return true;
        }
    }
}
