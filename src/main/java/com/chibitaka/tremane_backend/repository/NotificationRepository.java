package com.chibitaka.tremane_backend.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.entity.NotificationEntity;

/** 通知テーブルRepository */
@Mapper
public interface NotificationRepository {

    /** 登録 */
    void insert(NotificationEntity notification);

    /** ユーザーの通知一覧取得（作成日時の降順） */
    List<NotificationEntity> findByUserId(String userId);

    /** ユーザーの通知を全て既読 */
    void markAllReadByUserId(String userId);

    /** 通知の削除（1件） */
    void deleteById(String notificationId);

    /** 通知の削除（関連リソースID） */
    void deleteByRelatedId(String relatedId);

    /** 未読件数の取得 */
    int countUnreadNotificationsByUserId(String userId);
}
