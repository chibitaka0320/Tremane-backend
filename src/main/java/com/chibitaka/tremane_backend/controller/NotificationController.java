package com.chibitaka.tremane_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.NotificationDto;
import com.chibitaka.tremane_backend.service.NotificationService;

import lombok.RequiredArgsConstructor;

/** 通知用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService; // 通知Service

    /** ユーザー通知一覧の取得 */
    @GetMapping("")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        String userId = UserInfo.getUserId();
        List<NotificationDto> notificationDtos = notificationService.getNotifications(userId);

        return ResponseEntity.status(HttpStatus.OK).body(notificationDtos);
    }

    /** ユーザー通知の未読件数取得 */
    @GetMapping("/noread")
    public ResponseEntity<Integer> getNoreadCount() {
        String userId = UserInfo.getUserId();
        int count = notificationService.getUnreadNotificationsCount(userId);

        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    /** ユーザー通知を全て既読にする */
    @PutMapping("/read")
    public ResponseEntity<Void> markAllRead() {
        String userId = UserInfo.getUserId();
        notificationService.markAllRead(userId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
