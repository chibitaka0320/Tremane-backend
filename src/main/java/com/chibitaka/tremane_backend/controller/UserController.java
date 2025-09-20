package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.UserSearchResultDto;
import com.chibitaka.tremane_backend.dto.UserDto;
import com.chibitaka.tremane_backend.dto.UserGoalDto;
import com.chibitaka.tremane_backend.dto.UserProfileDto;
import com.chibitaka.tremane_backend.form.UserForm;
import com.chibitaka.tremane_backend.form.UserGoalForm;
import com.chibitaka.tremane_backend.form.UserProfileForm;
import com.chibitaka.tremane_backend.service.UserService;

import lombok.RequiredArgsConstructor;

/** ユーザー用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService; // ユーザーService

    /** ユーザー取得 */
    @GetMapping("")
    public ResponseEntity<UserDto> getUser() {
        String userId = UserInfo.getUserId();
        UserDto dto = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /** ユーザー更新 */
    @PutMapping("")
    public ResponseEntity<Void> updateUser(@RequestBody UserForm form) {
        String userId = UserInfo.getUserId();
        userService.updateUser(userId, form);

        // TODO: ステータス検討
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** ユーザー削除 */
    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser() {
        String userId = UserInfo.getUserId();
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** ユーザープロフィール取得 */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestParam LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        UserProfileDto userDto = userService.getUserProfileByUserId(userId, updatedAt);

        // TODO: エラーハンドリング検討
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
    }

    /** ユーザープロフィール追加更新 */
    @PostMapping("/profile")
    public ResponseEntity<Void> saveUserProfile(@RequestBody UserProfileForm form) {
        String userId = UserInfo.getUserId();
        userService.saveUserProfile(userId, form);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /* ユーザー目標取得 */
    @GetMapping("/goal")
    public ResponseEntity<UserGoalDto> getUserGoal(@RequestParam LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        UserGoalDto goalDto = userService.getUserGoalByUserId(userId, updatedAt);

        // TODO: エラーハンドリング検討
        if (goalDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(goalDto);
        }
    }

    /** ユーザー目標追加更新 */
    @PostMapping("/goal")
    public ResponseEntity<Void> saveUserGoal(@RequestBody UserGoalForm form) {
        String userId = UserInfo.getUserId();
        userService.saveUserGoal(userId, form);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ユーザーメールアドレス検索
    @GetMapping("/search")
    public ResponseEntity<UserSearchResultDto> searchUserByEmail(@RequestParam String email) {
        String userId = UserInfo.getUserId();
        UserSearchResultDto userDto = userService.getUserByEmail(email, userId);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
