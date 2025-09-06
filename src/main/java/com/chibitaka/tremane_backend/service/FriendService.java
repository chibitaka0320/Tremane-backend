package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;

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
        friendEntity.setStatus("pennding");

        friendRepository.insertFriendRequest(friendEntity);

        return friendEntity.getRequestId();
    }

    /** 友達申請削除 */
    public void deleteFriendRequest(String requestId) {
        friendRepository.deleteFriendRequest(requestId);
    }

    /** 友達申請許可 */
    public String receiveFriendRequest(String requestId, String userId) {

        final String STATUS = "accepted";

        // リクエスト検索
        FriendRequestEntity friendRequestEntity = friendRepository.findById(requestId);

        // リクエストなし及びステータスがpendingではない場合はnullを返す。
        if (friendRequestEntity == null || !"pending".equals(friendRequestEntity.getStatus())) {
            return null;
        }

        // 申請相手のリクエストをacceptに更新
        friendRequestEntity.setStatus(STATUS);
        friendRequestEntity.setUpdatedAt(LocalDateTime.now());
        friendRepository.updateFriendRequest(friendRequestEntity);

        // 自分のリクエストを作成
        FriendRequestEntity acceptRequestEntity = new FriendRequestEntity();
        acceptRequestEntity.setRequestUserId(userId);
        acceptRequestEntity.setReceiveUserId(friendRequestEntity.getRequestUserId());
        acceptRequestEntity.setStatus(STATUS);
        friendRepository.insertFriendRequest(acceptRequestEntity);

        return acceptRequestEntity.getRequestId();
    }

}
