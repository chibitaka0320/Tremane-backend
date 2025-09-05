package com.chibitaka.tremane_backend.repository;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.FriendRequestEntity;

/** 友達リクエストテーブル用 Repository */
@Mapper
public interface FriendRequestRepository {

    /** 友達リクエスト情報取得 */
    FriendRequestEntity getFirendRequest(String userId, String receiveUserId);
}
