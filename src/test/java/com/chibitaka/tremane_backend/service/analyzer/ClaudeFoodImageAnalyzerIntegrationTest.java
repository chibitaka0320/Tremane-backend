package com.chibitaka.tremane_backend.service.analyzer;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.chibitaka.tremane_backend.dto.MealAnalysisDto;

/**
 * 実際に Claude API を呼び出す結合テスト（課金が発生するため手動実行のみ）
 *
 * 実行方法:
 *   ANTHROPIC_API_KEY=... MEAL_ANALYSIS_TEST_IMAGE=/path/to/meal.jpg \
 *     ./mvnw test -Dtest=ClaudeFoodImageAnalyzerIntegrationTest -Djacoco.skip=true
 */
@EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".+")
@EnabledIfEnvironmentVariable(named = "MEAL_ANALYSIS_TEST_IMAGE", matches = ".+")
class ClaudeFoodImageAnalyzerIntegrationTest {

    @Test
    void 食事写真を解析できる() throws Exception {
        String apiKey = System.getenv("ANTHROPIC_API_KEY");
        ClaudeFoodImageAnalyzer analyzer = new ClaudeFoodImageAnalyzer(
                AnthropicOkHttpClient.builder().apiKey(apiKey).build(),
                apiKey, "claude-opus-5-5", "low");
        String image = Base64.getEncoder().encodeToString(
                Files.readAllBytes(Path.of(System.getenv("MEAL_ANALYSIS_TEST_IMAGE"))));

        MealAnalysisDto result = analyzer.analyze(image, "image/jpeg");

        System.out.println("解析結果: " + result);
        assertThat(result.mealName()).isNotBlank();
        assertThat(result.foods()).isNotEmpty();
    }
}
