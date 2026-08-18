package com.chibitaka.tremane_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** メール送信用Service（Resend API経由） */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    /** RestClient */
    private final RestClient restClient;

    /** Resend APIキー */
    @Value("${resend.api-key:}")
    private String resendApiKey;

    /** Resend APIエンドポイント */
    @Value("${resend.api-url}")
    private String resendApiUrl;

    /** 送信元アドレス */
    @Value("${resend.from-address}")
    private String fromAddress;

    /** プレーンテキストメール送信 */
    public void sendPlainTextEmail(String to, String subject, String text) {
        Map<String, Object> payload = Map.of(
                "from", fromAddress,
                "to", List.of(to),
                "subject", subject,
                "text", text);

        restClient.post()
                .uri(resendApiUrl)
                .header("Authorization", "Bearer " + resendApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toBodilessEntity();

        log.info("メール送信完了: to={}, subject={}", to, subject);
    }
}
