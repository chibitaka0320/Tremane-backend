package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Pattern;

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
import com.chibitaka.tremane_backend.repository.NotificationRepository;
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

    // ID（検索用ハンドル）フォーマット：英数字・._-のみ、8〜16文字
    private static final Pattern HANDLE_PATTERN = Pattern.compile("^[A-Za-z0-9_.-]{8,16}$");
    // ID変更後、再変更できるようになるまでの日数
    private static final long HANDLE_CHANGE_COOLDOWN_DAYS = 14;

    private final UserRepository userRepository; // ユーザーRepository
    private final UserGoalRepository userGoalRepository; // ユーザーゴールRepository
    private final UserProfileRepository userProfileRepository; // ユーザープロフィールRepository
    private final FriendRequestRepository friendRepository; // 友達リクエストRepository
    private final NotificationRepository notificationRepository; // 通知Repository
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
            if ("NOT_FOUND".equals(e.getErrorCode().name())) {
                throw new ApiResponseException(404, e.getErrorCode().toString(), e.getMessage());
            } else {
                throw new ApiResponseException(400, e.getErrorCode().toString(), e.getMessage());
            }
        }
    }

    /** ユーザー更新 */
    public void updateUser(String userId, UserForm form) {
        UserEntity entity = new UserEntity();
        entity.setUserId(userId);
        entity.setNickname(form.getNickname());
        entity.setUpdatedAt(form.getUpdatedAt());
        userRepository.update(entity);
    }

    /** ユーザーID（検索用ハンドル）更新 */
    public void updateUserHandle(String userId, String handle) {
        if (handle == null || !HANDLE_PATTERN.matcher(handle).matches()) {
            throw new ApiResponseException(400, "400", "IDは英数字と._-のみ使用可能で、8〜16文字で入力してください");
        }

        UserEntity currentUser = userRepository.findById(userId);
        if (currentUser == null) {
            throw new ApiResponseException(404, "404", "ユーザーが見つかりませんでした");
        }

        // 現在と全く同じ値であれば変更不要（クールダウン・重複チェックは行わない）
        if (handle.equals(currentUser.getHandle())) {
            return;
        }

        // 2回目以降の変更の場合のみクールダウンを判定する（初回登録は対象外）
        if (currentUser.getHandleUpdatedAt() != null) {
            LocalDateTime cooldownEnd = currentUser.getHandleUpdatedAt().plusDays(HANDLE_CHANGE_COOLDOWN_DAYS);
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(cooldownEnd)) {
                long remainingDays = ChronoUnit.DAYS.between(now, cooldownEnd) + 1;
                throw new ApiResponseException(409, "409", "IDの変更は14日に1回までです。あと" + remainingDays + "日で変更できます");
            }
        }

        // 重複チェック（大文字小文字区別なし、自分自身は除外）
        UserEntity existingUser = userRepository.findByHandle(handle);
        if (existingUser != null && !existingUser.getUserId().equals(userId)) {
            throw new ApiResponseException(409, "409", "このIDは既に使用されています");
        }

        UserEntity entity = new UserEntity();
        entity.setUserId(userId);
        entity.setHandle(handle);
        entity.setHandleUpdatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        userRepository.updateHandle(entity);
    }

    /** ユーザー削除 */
    public void deleteUser(String userId) {
        // 退会ユーザーが関わる友達リクエストは以降のユーザー削除でCASCADE削除されるが、
        // notifications.related_idは外部キーではなく連動しないため、相手側に残る通知を先に削除する
        List<FriendRequestEntity> friendRequests = friendRepository.findAllByUserId(userId);
        for (FriendRequestEntity friendRequest : friendRequests) {
            notificationRepository.deleteByRelatedId(friendRequest.getRequestId());
        }

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
