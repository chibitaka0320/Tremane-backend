package com.chibitaka.tremane_backend.service.analyzer;

import com.chibitaka.tremane_backend.dto.MealAnalysisDto;

/**
 * 食事画像解析インターフェース
 * 解析に使うAIサービスを差し替えられるよう、実装と分離している
 */
public interface FoodImageAnalyzer {

    /**
     * 食事画像から料理・食品とPFCを推定する
     *
     * @param imageBase64 画像（Base64）
     * @param mediaType   画像形式（image/jpeg など）
     * @return 解析結果
     */
    MealAnalysisDto analyze(String imageBase64, String mediaType);
}
