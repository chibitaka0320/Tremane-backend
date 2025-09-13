package com.chibitaka.tremane_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.NotificationDto;
import com.chibitaka.tremane_backend.entity.FriendRequestEntity;
import com.chibitaka.tremane_backend.entity.NotificationEntity;
import com.chibitaka.tremane_backend.repository.FriendRequestRepository;
import com.chibitaka.tremane_backend.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

/** 通知用Service */
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository; // 通知Repository
    private final FriendRequestRepository friendRepository; // 友達リクエストRepository

    /** ユーザー通知一覧の取得 */
    public List<NotificationDto> getNotifications(String userId) {
        List<NotificationEntity> notificationEntites = notificationRepository.findByUserId(userId);

        List<NotificationDto> notificationDtos = notificationEntites.stream().map(n -> {
            if ("FRIEND_REQUEST".equals(n.getType())) {
                FriendRequestEntity targetRequestEntity = friendRepository.findById(n.getRelatedId());
                return new NotificationDto(
                        n.getNotificationId(), n.getUserId(), n.getNotificationSource(), n.getType(), n.getMessage(),
                        n.getRelatedId(), n.isRead(),
                        n.getCreatedAt(), targetRequestEntity.getStatus());
            } else {
                // 現在はFRIEND_REQUESTのみ（今後拡張予定）
                return new NotificationDto();
            }
        }).toList();

        return notificationDtos;
    }

    /** ユーザー通知未読件数の取得 */
    public int getUnreadNotificationsCount(String userId) {
        return notificationRepository.countUnreadNotificationsByUserId(userId);
    }

    /** ユーザー通知を全て既読にする */
    public void markAllRead(String userId) {
        notificationRepository.markAllReadByUserId(userId);
    }
}
