package com.chibitaka.tremane_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
