package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.UserSearchResultDto;
import com.chibitaka.tremane_backend.dto.UserDto;
import com.chibitaka.tremane_backend.dto.UserGoalDto;
import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.entity.FriendRequestEntity;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.entity.UserGoalEntity;
import com.chibitaka.tremane_backend.entity.UserProfileEntity;
import com.chibitaka.tremane_backend.form.UserForm;
import com.chibitaka.tremane_backend.form.UserGoalForm;
import com.chibitaka.tremane_backend.form.UserProfileForm;
import com.chibitaka.tremane_backend.repository.FriendRequestRepository;
import com.chibitaka.tremane_backend.repository.UserGoalRepository;
import com.chibitaka.tremane_backend.repository.UserProfileRepository;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.RequiredArgsConstructor;

/** ユーザー関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository; // ユーザーRepository
    private final UserGoalRepository userGoalRepository; // ユーザーゴールRepository
    private final UserProfileRepository userProfileRepository; // ユーザープロフィールRepository
    private final FriendRequestRepository friendRepository; // 友達リクエストRepository
    private final ModelMapper modelMapper; // ModelMapper

    /** ユーザー取得（ID） */
    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findById(userId);
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    /** ユーザー取得（Email） */
    public UserSearchResultDto getUserByEmail(String email, String requestUserId) {
        UserSearchResultDto resultDto = new UserSearchResultDto();
        try {
            // firebaseからメールアドレス検索
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);

            // 取得したIDからフレンド情報取得
            String receiveUserId = userRecord.getUid();

            // 本人の場合は404エラーにする。
            // TODO: エラーステータスやハンドリングについては検討
            if (requestUserId.equals(receiveUserId)) {
                throw new ApiResponseException(404, "404", "見つかりませんでした");
            }
            resultDto.setUserId(receiveUserId);
            resultDto.setEmail(userRecord.getEmail());
            resultDto.setNickname(userRecord.getDisplayName());

            // 友達リクエスト状況を取得
            FriendRequestEntity friendRequestEntity = friendRepository.getFirendRequest(requestUserId, receiveUserId);
            if (friendRequestEntity != null) {
                resultDto.setStatus(friendRequestEntity.getStatus());
                resultDto.setRequestId(friendRequestEntity.getRequestId());
            } else {
                // 検索対象者からのリクエスト状況を取得
                FriendRequestEntity friendReceiveEntity = friendRepository.getFirendRequest(receiveUserId,
                        requestUserId);
                if (friendReceiveEntity != null) {
                    resultDto.setStatus("receive");
                    resultDto.setRequestId(friendReceiveEntity.getRequestId());
                }
            }
            return resultDto;
        } catch (FirebaseAuthException e) {
            // TODO: エラーステータスやハンドリングについては検討
            throw new ApiResponseException(400, e.getErrorCode().toString(), e.getMessage());
        }
    }

    /** ユーザー更新 */
    public void updateUser(String userId, UserForm form) {
        UserEntity entity = new UserEntity(userId, form.getNickname(), null, form.getUpdatedAt());
        userRepository.update(entity);
    }

    /** ユーザー削除 */
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    /** ユーザープロフィール取得 */
    public UserProfileDto getUserProfileByUserId(String userId, LocalDateTime updatedAt) {
        UserProfileEntity userProfileEntity = userProfileRepository.findById(userId, updatedAt);

        if (userProfileEntity == null) {
            return null;
        }

        UserProfileDto userProfileDto = modelMapper.map(userProfileEntity, UserProfileDto.class);
        return userProfileDto;
    }

    /** ユーザープロフィール追加更新 */
    public void saveUserProfile(String userId, UserProfileForm form) {
        UserProfileEntity userEntity = modelMapper.map(form, UserProfileEntity.class);
        userEntity.setUserId(userId);

        userProfileRepository.upsert(userEntity);
    }

    /** ユーザー目標取得 */
    public UserGoalDto getUserGoalByUserId(String userId, LocalDateTime updatedAt) {
        UserGoalEntity userGoalEntity = userGoalRepository.findById(userId, updatedAt);

        if (userGoalEntity == null) {
            return null;
        }

        UserGoalDto userGoalDto = modelMapper.map(userGoalEntity, UserGoalDto.class);
        return userGoalDto;
    }

    /** ユーザー目標追加更新 */
    public void saveUserGoal(String userId, UserGoalForm form) {
        UserGoalEntity entity = modelMapper.map(form, UserGoalEntity.class);
        entity.setUserId(userId);

        userGoalRepository.upsert(entity);
    }

}
