package com.chibitaka.tremane_backend.service;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.chibitaka.tremane_backend.dto.response.ExpoPushResponse;

import lombok.RequiredArgsConstructor;

/** プッシュ通知用Service */
@Service
@RequiredArgsConstructor
@Transactional
public class PushNotificationService {

    /** RestClient */
    private final RestClient restClient;

    /** Expo Push通知送信用エンドポイント */
    // TODO: propertiesファイルに移動
    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    /** プッシュ通知リトライ */
    public void sendPushNotificationWithRetry(String token, String title, String body, int maxRetries) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            boolean success = sendPushNotification(token, title, body);

            if (success) {
                System.out.println("Push送信成功");
                return;
            }
        }
        System.err.println("Push送信失敗");
    }

    /** プッシュ通知の送信 */
    private boolean sendPushNotification(String token, String title, String body) {
        Map<String, Object> payload = Map.of(
                "to", token,
                "sound", "default",
                "title", title,
                "body", body);

        try {
            // Expoプッシュ通知APIを実行
            ExpoPushResponse response = restClient.post().uri(EXPO_PUSH_URL).contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve().body(ExpoPushResponse.class);

            if (response != null && response.getData() != null) {
                String status = response.getData().getStatus();

                // 成功失敗を判定
                return "ok".equalsIgnoreCase(status);
            } else {
                System.err.println("レスポンス不正：" + response);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Expo Push送信例外：" + e.getMessage());
            return false;
        }
    }
}
