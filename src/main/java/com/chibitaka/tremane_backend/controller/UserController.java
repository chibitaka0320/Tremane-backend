package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.service.UserService;

import lombok.RequiredArgsConstructor;

/** ユーザー用コントローラー */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /** ユーザープロフィール情報取得 */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUser() {
        Long userId = UserInfo.getUserId();
        UserProfileDto userDto = userService.getUserInfo(userId);

        return ResponseEntity.ok(userDto);
    }
}
