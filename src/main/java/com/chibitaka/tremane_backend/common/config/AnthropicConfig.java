package com.chibitaka.tremane_backend.common.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;

/** Claude API クライアント設定クラス */
@Configuration
public class AnthropicConfig {

    /**
     * Claude API クライアント
     * APIキー未設定でもアプリ起動は妨げない（解析API呼び出し時にエラーとする）
     */
    @Bean
    AnthropicClient anthropicClient(
            @Value("${anthropic.api-key:}") String apiKey,
            @Value("${meal-analysis.timeout-seconds:30}") long timeoutSeconds) {
        AnthropicOkHttpClient.Builder builder = AnthropicOkHttpClient.builder()
                .timeout(Duration.ofSeconds(timeoutSeconds))
                // ユーザーを待たせすぎないよう、リトライは1回まで
                .maxRetries(1);
        if (!apiKey.isBlank()) {
            builder.apiKey(apiKey);
        }
        return builder.build();
    }
}
