package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.response.InsertFriendRequestResponseDto;
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
    public InsertFriendRequestResponseDto insertFriendRequest(String requestUserId, String receiveUserId) {

        InsertFriendRequestResponseDto resultDto = new InsertFriendRequestResponseDto();

        // 自分の申請を確認
        FriendRequestEntity friendRequestEntity = friendRepository.getFirendRequest(requestUserId, receiveUserId);
        if (friendRequestEntity != null) {
            resultDto.setRequestId(friendRequestEntity.getRequestId());
            resultDto.setStatus("conflict");
            return resultDto;
        }

        // 相手からの申請を確認
        FriendRequestEntity friendReceiveRequestEntity = friendRepository.getFirendRequest(receiveUserId,
                requestUserId);
        if (friendReceiveRequestEntity != null) {
            resultDto.setRequestId(friendReceiveRequestEntity.getRequestId());
            resultDto.setStatus("receive");
            return resultDto;
        }

        FriendRequestEntity friendEntity = new FriendRequestEntity();
        friendEntity.setRequestUserId(requestUserId);
        friendEntity.setReceiveUserId(receiveUserId);
        friendEntity.setStatus("pending");

        friendRepository.insertFriendRequest(friendEntity);

        resultDto.setRequestId(friendEntity.getRequestId());
        resultDto.setStatus("success");
        ;

        return resultDto;
    }

    /** 友達申請削除 */
    public boolean deleteFriendRequest(String requestId) {

        // 削除対象リクエストの検索
        FriendRequestEntity targetRequestEntity = friendRepository.findById(requestId);

        // 対象がなければ結果をfalseで返す
        if (targetRequestEntity == null) {
            return false;
        }

        // ステータス = pennding：対象リクエスト削除
        if ("pending".equals(targetRequestEntity.getStatus())) {
            friendRepository.deleteFriendRequest(requestId);
            return true;
        }

        // ステータス = accepted：対象リクエスト及び、自身がreceiveUserのリクエストを削除
        if ("accepted".equals(targetRequestEntity.getStatus())) {
            FriendRequestEntity targetRecieveRequestEntity = friendRepository
                    .getFirendRequest(targetRequestEntity.getReceiveUserId(), targetRequestEntity.getRequestUserId());

            friendRepository.deleteFriendRequest(targetRequestEntity.getRequestId());

            if (targetRecieveRequestEntity != null) {
                friendRepository.deleteFriendRequest(targetRecieveRequestEntity.getRequestId());
            }
            return true;
        }

        return false;
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
