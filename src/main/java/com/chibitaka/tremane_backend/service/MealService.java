package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.dto.MealDto;
import com.chibitaka.tremane_backend.entity.MealEntity;
import com.chibitaka.tremane_backend.form.MealForm;
import com.chibitaka.tremane_backend.repository.EatingRepository;
import com.chibitaka.tremane_backend.repository.MealRepository;

import lombok.RequiredArgsConstructor;

/** 食事記録関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class MealService {

    private final MealRepository mealRepository; // 食事記録Repository
    private final EatingRepository eatingRepository; // 食品Repository（カスケード削除用）
    private final ModelMapper modelMapper; // ModelMapper

    /** 食事記録更新情報取得 */
    public List<MealDto> getMealsByUserId(String userId, LocalDateTime updatedAt) {
        List<MealEntity> mealEntities = mealRepository.findByUserId(userId, updatedAt);
        List<MealDto> mealDtos = new ArrayList<>();

        for (MealEntity mealEntity : mealEntities) {
            MealDto mealDto = modelMapper.map(mealEntity, MealDto.class);
            mealDtos.add(mealDto);
        }

        return mealDtos;
    }

    /** 食事記録詳細取得 */
    public MealDto getMealById(String userId, String mealId) {
        MealEntity mealEntity = mealRepository.findById(mealId, userId);
        if (mealEntity == null) {
            throw new ApiResponseException(404, "404", "見つかりませんでした");
        }
        MealDto mealDto = modelMapper.map(mealEntity, MealDto.class);

        return mealDto;
    }

    /** 食事記録追加更新 */
    public void saveMeals(String userId, MealForm[] forms) {
        for (MealForm form : forms) {
            MealEntity mealEntity = modelMapper.map(form, MealEntity.class);
            mealEntity.setUserId(userId);

            mealRepository.upsert(mealEntity);
        }
    }

    /** 食事記録削除（内包する食品記録もカスケード削除する） */
    public void deleteMeal(String userId, String mealId) {
        eatingRepository.deleteByMealId(userId, mealId);
        mealRepository.delete(userId, mealId);
    }
}
