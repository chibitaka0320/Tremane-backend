package com.chibitaka.tremane_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.response.InsertFriendRequestResponseDto;
import com.chibitaka.tremane_backend.dto.response.TimelineTrainingResponseDto;
import com.chibitaka.tremane_backend.dto.response.TimelineTrainingResponseDto.TimelineBodyParts;
import com.chibitaka.tremane_backend.dto.response.TrainingRankingResponseDto;
import com.chibitaka.tremane_backend.entity.FriendRequestEntity;
import com.chibitaka.tremane_backend.entity.NotificationEntity;
import com.chibitaka.tremane_backend.repository.FriendRequestRepository;
import com.chibitaka.tremane_backend.repository.NotificationRepository;
import com.chibitaka.tremane_backend.repository.TrainingRepository;
import com.chibitaka.tremane_backend.repository.UserPushTokenRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GetUsersResult;
import com.google.firebase.auth.UidIdentifier;
import com.google.firebase.auth.UserIdentifier;
import com.google.firebase.auth.UserRecord;

import lombok.RequiredArgsConstructor;

/** 友達申請関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    private final FriendRequestRepository friendRepository; // 友達申請Repository
    private final TrainingRepository trainingRepository; // トレーニングRepository
    private final UserPushTokenRepository pushTokenRepository; // プッシュ通知トークンRepository
    private final NotificationRepository notificationRepository; // 通知Repository
    private final PushNotificationService pushNotificationService; // プッシュ通知Service

    /** 友達申請（追加） */
    public InsertFriendRequestResponseDto insertFriendRequest(String requestUserId, String receiveUserId) {

        InsertFriendRequestResponseDto resultDto = new InsertFriendRequestResponseDto();

        // 自分の申請を確認
        FriendRequestEntity friendRequestEntity = friendRepository.getFirendRequest(requestUserId, receiveUserId);
        if (friendRequestEntity != null) {
            resultDto.setRequestId(friendRequestEntity.getRequestId());
            resultDto.setStatus("conflict");
            return resultDto;
        }

        // 相手からの申請を確認
        FriendRequestEntity friendReceiveRequestEntity = friendRepository.getFirendRequest(receiveUserId,
                requestUserId);
        if (friendReceiveRequestEntity != null) {
            resultDto.setRequestId(friendReceiveRequestEntity.getRequestId());
            resultDto.setStatus("receive");
            return resultDto;
        }

        // 申請をDBに保存
        FriendRequestEntity friendEntity = new FriendRequestEntity();
        friendEntity.setRequestUserId(requestUserId);
        friendEntity.setReceiveUserId(receiveUserId);
        friendEntity.setStatus("pending");
        friendRepository.insertFriendRequest(friendEntity);

        // 作成されたリクエストIDと、結果をセットしreturn
        resultDto.setRequestId(friendEntity.getRequestId());
        resultDto.setStatus("success");

        // 通知テーブルにレコード追加
        NotificationEntity notificationEntity = new NotificationEntity(null, friendEntity.getReceiveUserId(),
                friendEntity.getRequestUserId(),
                "FRIEND_REQUEST", friendEntity.getRequestId(), requestUserId + " があなたに友達申請しました。", false, null, null);
        notificationRepository.insert(notificationEntity);

        // プッシュ通知処理
        String token = pushTokenRepository.findTokenByUserId(receiveUserId);
        String title = "友達申請が届きました";
        String body = "ユーザー " + requestUserId + " から友達リクエストがあります";
        int tryCount = 3;
        if (token != null) {
            try {
                pushNotificationService.sendPushNotificationWithRetry(token, title, body, tryCount);
            } catch (Exception e) {
                System.err.println("プッシュ通知に失敗しました：" + e.getMessage());
            }
        }

        return resultDto;
    }

    /** 友達申請削除 */
    public boolean deleteFriendRequest(String requestId) {

        // 削除対象リクエストの検索
        FriendRequestEntity targetRequestEntity = friendRepository.findById(requestId);

        // 対象がなければ結果をfalseで返す
        if (targetRequestEntity == null) {
            return false;
        }

        // ステータス = pennding：対象リクエスト削除
        if ("pending".equals(targetRequestEntity.getStatus())) {
            friendRepository.deleteFriendRequest(requestId);
            notificationRepository.deleteByRelatedId(requestId);
            return true;
        }

        // ステータス = accepted：対象リクエスト及び、自身がreceiveUserのリクエストを削除
        if ("accepted".equals(targetRequestEntity.getStatus())) {
            FriendRequestEntity targetRecieveRequestEntity = friendRepository
                    .getFirendRequest(targetRequestEntity.getReceiveUserId(), targetRequestEntity.getRequestUserId());

            friendRepository.deleteFriendRequest(targetRequestEntity.getRequestId());
            notificationRepository.deleteByRelatedId(targetRequestEntity.getRequestId());

            if (targetRecieveRequestEntity != null) {
                friendRepository.deleteFriendRequest(targetRecieveRequestEntity.getRequestId());
                notificationRepository.deleteByRelatedId(targetRecieveRequestEntity.getRequestId());
            }
            return true;
        }

        return false;
    }

    /** 友達申請許可 */
    public String receiveFriendRequest(String requestId, String userId) {

        final String STATUS = "accepted";

        // リクエスト検索
        FriendRequestEntity friendRequestEntity = friendRepository.findById(requestId);

        // リクエストなし及びステータスがpendingではない場合はnullを返す。
        if (friendRequestEntity == null || !"pending".equals(friendRequestEntity.getStatus())) {
            return null;
        }

        // 申請相手のリクエストをacceptに更新
        friendRequestEntity.setStatus(STATUS);
        friendRequestEntity.setUpdatedAt(LocalDateTime.now());
        friendRepository.updateFriendRequest(friendRequestEntity);

        // 自分のリクエストを作成
        FriendRequestEntity acceptRequestEntity = new FriendRequestEntity();
        acceptRequestEntity.setRequestUserId(userId);
        acceptRequestEntity.setReceiveUserId(friendRequestEntity.getRequestUserId());
        acceptRequestEntity.setStatus(STATUS);
        friendRepository.insertFriendRequest(acceptRequestEntity);

        return acceptRequestEntity.getRequestId();
    }

    /** 月間トレーニング数ランキング取得 */
    public List<TrainingRankingResponseDto> getRankingMonthly(String userId) {

        List<TrainingRankingResponseDto> rankingList = new ArrayList<>();

        // 友達一覧取得
        List<String> friendList = friendRepository.getFriends(userId);

        // 自身も含めてユーザー情報を取得
        friendList.add(userId);

        try {
            List<UserIdentifier> identifiers = new ArrayList<>();

            for (String uid : friendList) {
                identifiers.add(new UidIdentifier(uid));
            }
            GetUsersResult result = FirebaseAuth.getInstance().getUsers(identifiers);

            for (UserRecord userRecord : result.getUsers()) {
                TrainingRankingResponseDto rankingResponseDto = new TrainingRankingResponseDto();
                rankingResponseDto.setUserId(userRecord.getUid());
                rankingResponseDto.setNickname(userRecord.getDisplayName());

                rankingList.add(rankingResponseDto);
            }

        } catch (FirebaseAuthException e) {
            throw new ApiResponseException(400, e.getErrorCode().toString(), e.getMessage());
        }

        // 月別トレーニング情報の取得
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = now.with(TemporalAdjusters.lastDayOfMonth());
        List<Map<String, Object>> monthlyTrainingList = trainingRepository.getMonthlyTrainingCount(friendList,
                startDate,
                endDate);

        for (TrainingRankingResponseDto dto : rankingList) {
            for (Map<String, Object> map : monthlyTrainingList) {

                if (dto.getUserId().equals(String.valueOf(map.get("user_id")))) {
                    dto.setTrainingCounts(Integer.parseInt(String.valueOf(map.get("training_counts"))));
                }
            }
        }

        rankingList.sort(
                Comparator.comparingInt(TrainingRankingResponseDto::getTrainingCounts)
                        .reversed());

        return rankingList;
    }

    public List<TimelineTrainingResponseDto> getTimelineTraining(String userId) {
        // 友達一覧取得
        List<String> friendList = friendRepository.getFriends(userId);
        friendList.add(userId);

        List<Map<String, Object>> rows = trainingRepository.getTimelineTraining(friendList);
        Map<String, TimelineTrainingResponseDto> timelineMap = new LinkedHashMap<>();

        for (Map<String, Object> row : rows) {
            String id = row.get("user_id").toString();
            String nickname = row.get("nickname").toString();
            LocalDate date = LocalDate.parse(row.get("date").toString());
            String key = id + "_" + date;

            TimelineTrainingResponseDto dto = timelineMap.get(key);

            if (dto == null) {
                dto = new TimelineTrainingResponseDto();
                dto.setUserId(id);
                dto.setNickname(nickname);
                dto.setDate(date);
                dto.setBodyParts(new ArrayList<>());
                timelineMap.put(key, dto);
            }

            TimelineBodyParts bodyParts = new TimelineBodyParts();
            bodyParts.setPartsId(Integer.parseInt(row.get("parts_id").toString()));
            bodyParts.setBodyPartsName(row.get("body_parts_name").toString());
            dto.getBodyParts().add(bodyParts);
        }

        return new ArrayList<>(timelineMap.values());
    }

}
