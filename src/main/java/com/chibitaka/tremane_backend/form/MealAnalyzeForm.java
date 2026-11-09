package com.chibitaka.tremane_backend.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** 食事写真解析用Form */
@Data
public class MealAnalyzeForm {

    /** 画像（Base64）。デコード後5MB相当まで */
    @NotBlank
    @Size(max = 7_000_000)
    private String image;

    /** 画像形式（image/jpeg, image/png, image/webp） */
    @NotBlank
    private String mediaType;
}
