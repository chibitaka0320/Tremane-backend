package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.UserAccountInfoDto;
import com.chibitaka.tremane_backend.dto.UserDto;
import com.chibitaka.tremane_backend.dto.UserGoalDto;
import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.entity.UserGoalEntity;
import com.chibitaka.tremane_backend.entity.UserProfileEntity;
import com.chibitaka.tremane_backend.form.UserGoalForm;
import com.chibitaka.tremane_backend.form.UserProfileForm;
import com.chibitaka.tremane_backend.repository.UserGoalRepository;
import com.chibitaka.tremane_backend.repository.UserProfileRepository;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.RequiredArgsConstructor;

/** ユーザーサービスクラス */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final UserGoalRepository userGoalRepository;
    private final UserRepository userRepository;

    public UserDto getUser(String userId) {
        UserDto dto = userRepository.findById(userId);
        return dto;
    }

    /** ユーザープロフィール情報取得 */
    public UserProfileDto getUserInfo(String userId, LocalDateTime updatedAt) {

        UserProfileEntity userEntity = userProfileRepository.findById(userId, updatedAt);

        if (userEntity == null) {
            return null;
        }

        UserProfileDto userDto = new UserProfileDto();
        userDto.setUserId(userId);
        userDto.setNickname(userEntity.getNickname());
        userDto.setHeight(userEntity.getHeight());
        userDto.setWeight(userEntity.getWeight());
        userDto.setBirthday(userEntity.getBirthday());
        userDto.setGender(userEntity.getGender());
        userDto.setActiveLevel(userEntity.getActiveLevel());
        userDto.setCreatedAt(userEntity.getCreatedAt());
        userDto.setUpdatedAt(userEntity.getUpdatedAt());

        return userDto;
    }

    /** プロフィール情報追加更新 */
    public void upsertUserInfo(String userId, UserProfileForm form) {
        UserProfileEntity userEntity = new UserProfileEntity();
        userEntity.setUserId(userId);
        userEntity.setNickname(form.getNickname());
        userEntity.setHeight(form.getHeight());
        userEntity.setWeight(form.getWeight());
        userEntity.setBirthday(form.getBirthday());
        userEntity.setGender(form.getGender());
        userEntity.setActiveLevel(form.getActiveLevel());
        userEntity.setCreatedAt(form.getCreatedAt());
        userEntity.setUpdatedAt(form.getUpdatedAt());

        userProfileRepository.upsert(userEntity);
    }

    /** 目標取得 */
    public UserGoalDto getUserGoal(String userId, LocalDateTime updatedAt) {

        // 目標を取得し未設定であればnullを返す
        UserGoalEntity goalEntity = userGoalRepository.findById(userId, updatedAt);

        if (goalEntity == null) {
            return null;
        }

        UserGoalDto dto = new UserGoalDto();
        dto.setUserId(userId);
        dto.setWeight(goalEntity.getWeight());
        dto.setGoalWeight(goalEntity.getGoalWeight());
        dto.setStart(goalEntity.getStart());
        dto.setFinish(goalEntity.getFinish());
        dto.setPfc(goalEntity.getPfc());
        dto.setCreatedAt(goalEntity.getCreatedAt());
        dto.setUpdatedAt(goalEntity.getUpdatedAt());

        return dto;
    }

    /** 目標設定 */
    public void upsertUserGoal(String userId, UserGoalForm form) {
        UserGoalEntity entity = new UserGoalEntity();
        entity.setUserId(userId);
        entity.setWeight(form.getWeight());
        entity.setGoalWeight(form.getGoalWeight());
        entity.setStart(form.getStart());
        entity.setFinish(form.getFinish());
        entity.setPfc(form.getPfc());
        entity.setCreatedAt(form.getCreatedAt());
        entity.setUpdatedAt(form.getUpdatedAt());

        userGoalRepository.upsert(entity);
    }

    /** ユーザー削除 */
    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }

    /** ユーザーEmail検索 */
    public UserAccountInfoDto searchUserByEmail(String email) {
        UserAccountInfoDto userDto = new UserAccountInfoDto();
        try {
            UserRecord record = FirebaseAuth.getInstance().getUserByEmail(email);
            userDto.setUserId(record.getUid());
            userDto.setEmail(record.getEmail());
            userDto.setNickname(record.getDisplayName());
            userDto.setStatus("accepted");

            return userDto;
        } catch (FirebaseAuthException e) {
            throw new ApiResponseException(400, e.getErrorCode().toString(), e.getMessage());
        }
    }
}
