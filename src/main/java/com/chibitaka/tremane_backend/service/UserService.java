package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/** ユーザーサービスクラス */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

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
}
