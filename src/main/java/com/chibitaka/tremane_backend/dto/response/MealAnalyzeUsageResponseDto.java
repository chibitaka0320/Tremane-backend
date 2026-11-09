package com.chibitaka.tremane_backend.dto.response;

/** 食事写真解析の利用状況レスポンス */
public record MealAnalyzeUsageResponseDto(
        int remainingCount, // 本日の残り解析回数
        int dailyLimit) { // 1日あたりの解析回数上限
}
