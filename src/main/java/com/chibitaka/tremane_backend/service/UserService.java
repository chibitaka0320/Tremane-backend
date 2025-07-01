package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.UserProfileForm;
import com.chibitaka.tremane_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/** ユーザーサービスクラス */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /** ユーザープロフィール情報取得 */
    public UserProfileDto getUserInfo(Long userId) {

        UserEntity userEntity = userRepository.findById(userId);

        UserProfileDto userDto = new UserProfileDto();
        userDto.setNickname(userEntity.getNickname());
        userDto.setHeight(userEntity.getHeight());
        userDto.setWeight(userEntity.getWeight());
        userDto.setBirthday(userEntity.getBirthday());
        userDto.setGender(userEntity.getGender());
        userDto.setActiveLevel(userEntity.getActiveLevel());

        return userDto;
    }

    /** プロフィール情報更新 */
    public void updateUserInfo(Long userId, UserProfileForm form) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setNickname(form.getNickname());
        userEntity.setHeight(form.getHeight());
        userEntity.setWeight(form.getWeight());
        userEntity.setBirthday(form.getBirthday());
        userEntity.setGender(form.getGender());
        userEntity.setActiveLevel(form.getActiveLevel());

        userRepository.update(userEntity);
    }
}
