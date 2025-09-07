package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.response.InsertFriendRequestResponseDto;
import com.chibitaka.tremane_backend.service.FriendService;

import lombok.RequiredArgsConstructor;

/** 友達申請関連Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    /** 友達申請Service */
    private final FriendService friendService;

    /** 友達申請（追加） */
    @PostMapping("/{receiveUserId}")
    public ResponseEntity<String> requestFriend(@PathVariable String receiveUserId) {
        String userId = UserInfo.getUserId();

        InsertFriendRequestResponseDto resultDto = friendService.insertFriendRequest(userId, receiveUserId);
        String requestId = resultDto.getRequestId();
        String status = resultDto.getStatus();

        if ("success".equals(status)) {
            return ResponseEntity.ok(requestId);
        }

        else if ("conflict".equals(status)) {
            return ResponseEntity.status(409).body(requestId);
        }

        else if ("receive".equals(status)) {
            return ResponseEntity.status(418).body(requestId);
        }

        else {
            return ResponseEntity.badRequest().body(null);
        }

    }

    /** 友達取り消し（友達取り消し、申請取り消し、申請拒否） */
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> revokeFriend(@PathVariable String requestId) {

        friendService.deleteFriendRequest(requestId);

        return ResponseEntity.ok().build();

    }

    /** 友達申請許可 */
    @PutMapping("/{requestId}/accept")
    public ResponseEntity<String> acceptFriend(@PathVariable String requestId) {
        String userId = UserInfo.getUserId();
        requestId = friendService.receiveFriendRequest(requestId, userId);

        if (requestId == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(requestId);
        }
    }
}
