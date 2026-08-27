package com.chibitaka.tremane_backend.service;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.NotificationDto;
import com.chibitaka.tremane_backend.entity.FriendRequestEntity;
import com.chibitaka.tremane_backend.entity.NotificationEntity;
import com.chibitaka.tremane_backend.repository.FriendRequestRepository;
import com.chibitaka.tremane_backend.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

/** 通知関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository; // 通知Repository
    private final FriendRequestRepository friendRepository; // 友達リクエストRepository
    private final ModelMapper modelMapper; // ModelMapper

    /** ユーザー通知一覧の取得 */
    public List<NotificationDto> getNotificationsByUserId(String userId) {
        List<NotificationEntity> notificationEntites = notificationRepository.findByUserId(userId);

        List<NotificationDto> notificationDtos = notificationEntites.stream().map(notificationEntity -> {
            // 通知タイプが友達申請の場合
            if ("FRIEND_REQUEST".equals(notificationEntity.getType())) {
                // 申請状況を取得する
                FriendRequestEntity targetRequestEntity = friendRepository.findById(notificationEntity.getRelatedId());

                // 対象の友達申請が既に存在しない場合（相手アカウントの退会等）は通知として表示しない
                if (targetRequestEntity == null) {
                    return null;
                }

                NotificationDto notificationDto = modelMapper.map(notificationEntity, NotificationDto.class);
                notificationDto.setStatus(targetRequestEntity.getStatus());

                return notificationDto;
            }

            // その他
            else {
                // 現在はFRIEND_REQUESTのみ（今後拡張予定）
                return new NotificationDto();
            }
        }).filter(Objects::nonNull).toList();

        return notificationDtos;
    }

    /** ユーザー通知未読件数の取得 */
    public int getUnreadNotificationsCountByUserId(String userId) {
        return notificationRepository.countUnreadNotificationsByUserId(userId);
    }

    /** ユーザー通知を全て既読にする */
    public void markAllReadByUserId(String userId) {
        notificationRepository.markAllReadByUserId(userId);
    }
}
