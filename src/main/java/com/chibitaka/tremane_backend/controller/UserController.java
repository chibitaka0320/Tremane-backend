package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.UserAccountInfoDto;
import com.chibitaka.tremane_backend.dto.UserDto;
import com.chibitaka.tremane_backend.dto.UserGoalDto;
import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.form.UserGoalForm;
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
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestParam LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        UserProfileDto userDto = userService.getUserInfo(userId, updatedAt);

        if (userDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userDto);
        }
    }

    /** ユーザープロフィール情報追加更新 */
    @PostMapping("/profile")
    public ResponseEntity<Void> updateUser(@RequestBody UserProfileForm form) {
        String userId = UserInfo.getUserId();
        userService.upsertUserInfo(userId, form);

        return ResponseEntity.status(204).build();
    }

    /* ユーザー目標取得 */
    @GetMapping("/goal")
    public ResponseEntity<UserGoalDto> getUserGoal(@RequestParam LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        UserGoalDto goalDto = userService.getUserGoal(userId, updatedAt);

        if (goalDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(goalDto);
        }
    }

    @PostMapping("/goal")
    public ResponseEntity<Void> updateUserGoal(@RequestBody UserGoalForm form) {
        String userId = UserInfo.getUserId();
        userService.upsertUserGoal(userId, form);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getUser() {
        String userId = UserInfo.getUserId();
        UserDto dto = userService.getUser(userId);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser() {
        String userId = UserInfo.getUserId();
        userService.deleteUser(userId);

        return ResponseEntity.status(204).build();
    }

    // ユーザーメールアドレス検索
    @GetMapping("/search")
    public ResponseEntity<UserAccountInfoDto> searchUserByEmail(@RequestParam String email) {
        String userId = UserInfo.getUserId();
        UserAccountInfoDto userDto = userService.searchUserByEmail(email, userId);

        return ResponseEntity.ok(userDto);
    }
}
