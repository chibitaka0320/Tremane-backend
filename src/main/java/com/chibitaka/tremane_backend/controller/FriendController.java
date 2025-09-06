package com.chibitaka.tremane_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
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

        String requestId = friendService.insertFriendRequest(userId, receiveUserId);

        return ResponseEntity.ok(requestId);
    }

    /** 友達取り消し（友達取り消し、申請取り消し、申請拒否） */
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> revokeFriend(@PathVariable String requestId) {

        friendService.deleteFriendRequest(requestId);

        return ResponseEntity.ok().build();
    }
}
