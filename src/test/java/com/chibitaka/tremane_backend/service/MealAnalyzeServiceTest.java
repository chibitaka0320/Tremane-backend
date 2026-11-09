package com.chibitaka.tremane_backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.MealAnalysisDto;
import com.chibitaka.tremane_backend.dto.MealAnalysisDto.Food;
import com.chibitaka.tremane_backend.form.MealAnalyzeForm;
import com.chibitaka.tremane_backend.service.analyzer.FoodImageAnalyzer;

@ExtendWith(MockitoExtension.class)
class MealAnalyzeServiceTest {

    private static final String USER_ID = "user-1";
    private static final String IMAGE = Base64.getEncoder().encodeToString(new byte[] { 1, 2, 3 });

    @Mock
    private FoodImageAnalyzer foodImageAnalyzer;

    @InjectMocks
    private MealAnalyzeService mealAnalyzeService;

    private static MealAnalyzeForm form(String image, String mediaType) {
        MealAnalyzeForm form = new MealAnalyzeForm();
        form.setImage(image);
        form.setMediaType(mediaType);
        return form;
    }

    @Test
    void 同じユーザーの解析は1日5回まで() {
        when(foodImageAnalyzer.analyze(anyString(), anyString())).thenReturn(new MealAnalysisDto(null, null));

        for (int i = 0; i < 5; i++) {
            mealAnalyzeService.analyze(USER_ID, form(IMAGE, "image/jpeg"));
        }

        assertThatThrownBy(() -> mealAnalyzeService.analyze(USER_ID, form(IMAGE, "image/jpeg")))
                .isInstanceOfSatisfying(ApiResponseException.class,
                        e -> assertThat(e.getResponseCode()).isEqualTo(429));
        verify(foodImageAnalyzer, times(5)).analyze(anyString(), anyString());

        mealAnalyzeService.analyze("user-2", form(IMAGE, "image/jpeg"));
        verify(foodImageAnalyzer, times(6)).analyze(anyString(), anyString());
    }

    @Test
    void 解析結果を整えて返す() {
        when(foodImageAnalyzer.analyze(IMAGE, "image/jpeg")).thenReturn(new MealAnalysisDto(
                " 焼き魚定食 ",
                List.of(
                        new Food(" ご飯 ", 3.84, 0.46, 55.66),
                        new Food("焼き鮭", 18.0, -1.0, 0.1),
                        new Food(" ", 1, 1, 1))));

        MealAnalysisDto result = mealAnalyzeService.analyze(USER_ID, form(IMAGE, "image/jpeg"));

        assertThat(result.mealName()).isEqualTo("焼き魚定食");
        assertThat(result.foods()).containsExactly(
                new Food("ご飯", 3.8, 0.5, 55.7),
                new Food("焼き鮭", 18.0, 0.0, 0.1));
    }

    @Test
    void 食べ物が写っていなければ空の結果を返す() {
        when(foodImageAnalyzer.analyze(anyString(), anyString())).thenReturn(new MealAnalysisDto(null, null));

        MealAnalysisDto result = mealAnalyzeService.analyze(USER_ID, form(IMAGE, "image/png"));

        assertThat(result.mealName()).isEmpty();
        assertThat(result.foods()).isEmpty();
    }

    @Test
    void 対応していない画像形式は400() {
        assertThatThrownBy(() -> mealAnalyzeService.analyze(USER_ID, form(IMAGE, "image/gif")))
                .isInstanceOfSatisfying(ApiResponseException.class,
                        e -> assertThat(e.getResponseCode()).isEqualTo(400));
        verify(foodImageAnalyzer, never()).analyze(any(), any());
    }

    @Test
    void Base64として不正な画像は400() {
        assertThatThrownBy(() -> mealAnalyzeService.analyze(USER_ID, form("これは画像ではない", "image/jpeg")))
                .isInstanceOfSatisfying(ApiResponseException.class,
                        e -> assertThat(e.getResponseCode()).isEqualTo(400));
        verify(foodImageAnalyzer, never()).analyze(any(), any());
    }

    @Test
    void 上限を超える画像は400() {
        byte[] tooLarge = new byte[5 * 1024 * 1024 + 1];
        Arrays.fill(tooLarge, (byte) 1);
        String image = Base64.getEncoder().encodeToString(tooLarge);

        assertThatThrownBy(() -> mealAnalyzeService.analyze(USER_ID, form(image, "image/jpeg")))
                .isInstanceOfSatisfying(ApiResponseException.class,
                        e -> assertThat(e.getResponseCode()).isEqualTo(400));
        verify(foodImageAnalyzer, never()).analyze(any(), any());
    }
}
