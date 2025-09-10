package com.chibitaka.tremane_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.NotificationDto;
import com.chibitaka.tremane_backend.entity.NotificationEntity;
import com.chibitaka.tremane_backend.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

/** 通知用Service */
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository; // 通知Repository

    /** ユーザー通知一覧の取得 */
    public List<NotificationDto> getNotifications(String userId) {
        List<NotificationEntity> notificationEntites = notificationRepository.findByUserId(userId);

        List<NotificationDto> notificationDtos = notificationEntites.stream().map(n -> {
            return new NotificationDto(
                    n.getNotificationId(), n.getUserId(), n.getType(), n.getMessage(), n.getRelatedId(), n.isRead(),
                    n.getCreatedAt());
        }).toList();

        return notificationDtos;
    }
}
