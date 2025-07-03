package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.UserGoalDto;
import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.form.UserProfileForm;
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

        if (userDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userDto);
        }
    }

    /** ユーザープロフィール情報追加更新 */
    @PostMapping("/profile")
    public ResponseEntity<Void> updateUser(@RequestBody UserProfileForm form) {
        Long userId = UserInfo.getUserId();
        userService.upsertUserInfo(userId, form);

        return ResponseEntity.status(204).build();
    }

    /* ユーザー目標取得 */
    @GetMapping("/goal")
    public ResponseEntity<UserGoalDto> getUserGoal() {
        Long userId = UserInfo.getUserId();
        UserGoalDto goalDto = userService.getUserGoal(userId);

        if (goalDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(goalDto);
        }
    }
}
