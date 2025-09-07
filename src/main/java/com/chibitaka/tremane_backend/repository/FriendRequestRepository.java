package com.chibitaka.tremane_backend.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.FriendRequestEntity;

/** 友達リクエストテーブル用 Repository */
@Mapper
public interface FriendRequestRepository {

    /** 友達リクエスト情報取得 */
    FriendRequestEntity getFirendRequest(String userId, String receiveUserId);

    /** 友達リクエスト情報検索 */
    FriendRequestEntity findById(String requestId);

    /** 友達リクエスト追加（戻り値：リクエストID） */
    void insertFriendRequest(FriendRequestEntity entity);

    /** 友達リクエスト削除 */
    void deleteFriendRequest(String requestId);

    /** 友達リクエスト更新 */
    void updateFriendRequest(FriendRequestEntity entity);

    /** 友達一覧取得 */
    List<String> getFriends(String userId);
}
