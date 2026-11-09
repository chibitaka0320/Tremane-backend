package com.chibitaka.tremane_backend.service.analyzer;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.anthropic.client.AnthropicClient;
import com.anthropic.errors.AnthropicException;
import com.anthropic.errors.AnthropicIoException;
import com.anthropic.errors.AnthropicServiceException;
import com.anthropic.models.messages.Base64ImageSource;
import com.anthropic.models.messages.ContentBlockParam;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.OutputConfig;
import com.anthropic.models.messages.StopReason;
import com.anthropic.models.messages.StructuredMessage;
import com.anthropic.models.messages.StructuredMessageCreateParams;
import com.anthropic.models.messages.StructuredOutputConfig;
import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.MealAnalysisDto;

import lombok.extern.slf4j.Slf4j;

/** Claude API による食事画像解析 */
@Slf4j
@Component
public class ClaudeFoodImageAnalyzer implements FoodImageAnalyzer {

    private static final String SYSTEM_PROMPT = """
            あなたは管理栄養士です。ユーザーが送る食事の写真から、写っている料理・食品とその栄養素を推定してください。

            - mealName: 食事全体を表す短い日本語の名前（例:「焼き魚定食」「カツ丼」）
            - foods: 料理・食品ごとの一覧
              - 定食やワンプレートなど、複数の品がはっきり分かれている場合は品ごとに分ける
              - 丼・ラーメン・サンドイッチなど一品として食べるものは1件にまとめる
              - 調味料や飲み物は、はっきり写っている場合のみ含める
            - name: 一般的な日本語の料理名・食品名
            - protein / fat / carbo: 写真に写っている量（見えなければ一般的な1人前）を食べた場合のグラム数。小数第1位まで
            - 食べ物が写っていない場合は mealName を空文字、foods を空配列にする""";

    private static final long MAX_TOKENS = 16000L;

    private final AnthropicClient client; // Claude API クライアント
    private final boolean apiKeyConfigured; // APIキー設定有無
    private final String model; // 使用モデル
    private final OutputConfig.Effort effort; // 推論の深さ

    public ClaudeFoodImageAnalyzer(
            AnthropicClient client,
            @Value("${anthropic.api-key:}") String apiKey,
            @Value("${meal-analysis.model:claude-opus-5-5}") String model,
            @Value("${meal-analysis.effort:low}") String effort) {
        this.client = client;
        this.apiKeyConfigured = !apiKey.isBlank();
        this.model = model;
        this.effort = OutputConfig.Effort.of(effort);
        if (!apiKeyConfigured) {
            log.warn("ANTHROPIC_API_KEY が未設定のため、食事写真解析は利用できません");
        }
    }

    @Override
    public MealAnalysisDto analyze(String imageBase64, String mediaType) {
        if (!apiKeyConfigured) {
            throw new ApiResponseException(503, "503", "現在、写真解析は利用できません");
        }

        StructuredMessageCreateParams<MealAnalysisDto> params = MessageCreateParams.builder()
                .model(model)
                .maxTokens(MAX_TOKENS)
                .system(SYSTEM_PROMPT)
                .outputConfig(StructuredOutputConfig.<MealAnalysisDto>builder()
                        .effort(effort)
                        .format(MealAnalysisDto.class)
                        .build())
                .addUserMessageOfContentBlockParams(List.of(
                        ContentBlockParam.ofImage(Base64ImageSource.builder()
                                .data(imageBase64)
                                .mediaType(Base64ImageSource.MediaType.of(mediaType))
                                .build()),
                        ContentBlockParam.ofText("この食事を解析してください。")))
                .build();

        long started = System.currentTimeMillis();
        StructuredMessage<MealAnalysisDto> response;
        try {
            response = client.messages().create(params);
        } catch (AnthropicServiceException e) {
            // 429（レート制限）・5xx（障害・過負荷）は一時的なものとして扱う
            if (e.statusCode() == 429 || e.statusCode() >= 500) {
                log.warn("Claude API 一時エラー: status={}", e.statusCode());
                throw new ApiResponseException(503, "503", "現在混み合っています。時間をおいて再度お試しください");
            }
            // 4xx はキーや設定の不備のため、運用側で対応が必要
            log.error("Claude API エラー: status={}, message={}", e.statusCode(), e.getMessage());
            throw new ApiResponseException(502, "502", "写真の解析に失敗しました");
        } catch (AnthropicIoException e) {
            log.warn("Claude API 通信エラー（タイムアウト含む）: {}", e.getMessage());
            throw new ApiResponseException(504, "504", "解析がタイムアウトしました。時間をおいて再度お試しください");
        }
        long elapsed = System.currentTimeMillis() - started;

        StopReason stopReason = response.stopReason().orElse(null);
        log.info("食事写真解析: model={}, {}ms, stopReason={}, inputTokens={}, outputTokens={}",
                model, elapsed, stopReason, response.usage().inputTokens(), response.usage().outputTokens());

        if (StopReason.REFUSAL.equals(stopReason)) {
            throw new ApiResponseException(422, "422", "この写真は解析できませんでした");
        }
        if (StopReason.MAX_TOKENS.equals(stopReason)) {
            throw new ApiResponseException(502, "502", "写真の解析に失敗しました");
        }

        try {
            return response.content().stream()
                    .flatMap(block -> block.text().stream())
                    .map(textBlock -> textBlock.text())
                    .findFirst()
                    .orElseThrow(() -> new ApiResponseException(502, "502", "写真の解析に失敗しました"));
        } catch (AnthropicException e) {
            // 構造化出力のJSON変換失敗
            log.error("Claude API 応答の変換に失敗: {}", e.getMessage());
            throw new ApiResponseException(502, "502", "写真の解析に失敗しました");
        }
    }
}
