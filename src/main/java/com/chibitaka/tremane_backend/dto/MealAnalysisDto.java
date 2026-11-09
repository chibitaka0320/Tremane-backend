package com.chibitaka.tremane_backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 食事写真解析結果DTO
 * Claude API の構造化出力（JSON Schema）の定義も兼ねる
 */
public record MealAnalysisDto(
        @JsonPropertyDescription("食事全体を表す短い日本語の名前。食べ物が写っていない場合は空文字") String mealName,
        @JsonPropertyDescription("写っている料理・食品の一覧。食べ物が写っていない場合は空配列") List<Food> foods) {

    /** 推定した料理・食品 */
    public record Food(
            @JsonPropertyDescription("一般的な日本語の料理名・食品名") String name,
            @JsonPropertyDescription("たんぱく質（g）") double protein,
            @JsonPropertyDescription("脂質（g）") double fat,
            @JsonPropertyDescription("炭水化物（g）") double carbo) {
    }
}
