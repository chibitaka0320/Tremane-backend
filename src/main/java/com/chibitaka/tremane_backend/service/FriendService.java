package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.entity.FriendRequestEntity;
import com.chibitaka.tremane_backend.repository.FriendRequestRepository;

import lombok.RequiredArgsConstructor;

/** 友達申請関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    /** 友達申請Repository */
    private final FriendRequestRepository friendRepository;

    /** 友達申請（追加） */
    public String insertFriendRequest(String requestUserId, String receiveUserId) {
        FriendRequestEntity friendEntity = new FriendRequestEntity();
        friendEntity.setRequestUserId(requestUserId);
        friendEntity.setReceiveUserId(receiveUserId);

        friendRepository.insertFriendRequest(friendEntity);

        return friendEntity.getRequestId();
    }

}
