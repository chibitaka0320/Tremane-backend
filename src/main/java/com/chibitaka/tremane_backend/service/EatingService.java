package com.chibitaka.tremane_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.common.util.Calc;
import com.chibitaka.tremane_backend.dto.EatingDto;
import com.chibitaka.tremane_backend.dto.EatingRecordDto;
import com.chibitaka.tremane_backend.entity.EatingEntity;
import com.chibitaka.tremane_backend.entity.UserGoalEntity;
import com.chibitaka.tremane_backend.entity.UserProfileEntity;
import com.chibitaka.tremane_backend.form.EatingForm;
import com.chibitaka.tremane_backend.mapper.EatingMapper;
import com.chibitaka.tremane_backend.repository.EatingRepository;
import com.chibitaka.tremane_backend.repository.UserGoalRepository;
import com.chibitaka.tremane_backend.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

/** 食事記録サービスクラス */
@Service
@RequiredArgsConstructor
@Transactional
public class EatingService {

    private final EatingRepository eatingRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserGoalRepository userGoalRepository;

    /** 食事更新記録取得 */
    public List<EatingDto> getUpdateEatings(String userId, LocalDateTime updatedAt) {
        List<EatingDto> eatingDtos = eatingRepository.getEatings(userId, updatedAt);
        return eatingDtos;
    }

    /** 食事記録取得 */
    public EatingRecordDto getEatings(String userId, LocalDate date) {
        // 食事記録一覧取得
        List<EatingEntity> eatings = eatingRepository.findByUserIdAndDate(userId, date);

        EatingRecordDto eatingDto = new EatingRecordDto();
        eatingDto.setDate(date);
        eatingDto.setMeals(EatingMapper.toDtoList(eatings));
        eatingDto.setTotal(calcTotal(eatings));

        // ユーザー情報取得
        UserProfileEntity profileEntity = userProfileRepository.findById(userId, null);
        UserGoalEntity goalEntity = userGoalRepository.findById(userId);

        // 一日の目標カロリー
        Integer goalCalorie = Calc.getGoalCalorie(profileEntity, goalEntity);

        // 目標セット
        if (goalEntity != null) {
            setGoal(goalCalorie, goalEntity.getPfc(), eatingDto);
        }

        // 比率セット
        setRate(eatingDto);

        return eatingDto;
    }

    /** 食事記録詳細取得 */
    public EatingDto getEating(String eatingId) {
        EatingDto dto = eatingRepository.findById(eatingId);
        return dto;
    }

    /** 食事記録追加 */
    public void addEating(String userId, EatingForm form) {
        EatingEntity entity = new EatingEntity();
        entity.setDate(form.getDate());
        entity.setUserId(userId);
        entity.setName(form.getName());
        entity.setProtein(form.getProtein());
        entity.setFat(form.getFat());
        entity.setCarbo(form.getCarbo());

        entity.setCalories(calcKcal(entity));

        eatingRepository.insertEating(entity);
    }

    /** 食事記録更新 */
    public void updateEating(String userId, String eatingId, EatingForm form) {
        EatingEntity entity = new EatingEntity();
        entity.setEatingId(eatingId);
        entity.setDate(form.getDate());
        entity.setUserId(userId);
        entity.setName(form.getName());
        entity.setProtein(form.getProtein());
        entity.setFat(form.getFat());
        entity.setCarbo(form.getCarbo());

        entity.setCalories(calcKcal(entity));

        eatingRepository.update(entity);
    }

    /** 食事記録削除 */
    public void deleteEating(String userId, String eatingId) {
        eatingRepository.delete(userId, eatingId);
    }

    /** カロリー計算 */
    private int calcKcal(EatingEntity eating) {
        double protein = eating.getProtein() * 4;
        double fat = eating.getFat() * 9;
        double carbo = eating.getCarbo() * 4;

        int total = (int) Math.round(protein + fat + carbo);
        return total;
    }

    /** 食事合計算出 */
    private EatingRecordDto.TotalDto calcTotal(List<EatingEntity> eatings) {
        int totalCalories = 0;
        double totalProtein = 0;
        double totalFat = 0;
        double totalCarbo = 0;

        for (EatingEntity eating : eatings) {
            totalCalories += eating.getCalories();
            totalProtein += eating.getProtein();
            totalFat += eating.getFat();
            totalCarbo += eating.getCarbo();
        }

        EatingRecordDto.TotalDto totalDto = new EatingRecordDto.TotalDto();
        totalDto.setCalories(totalCalories);
        totalDto.setProtein(totalProtein);
        totalDto.setFat(totalFat);
        totalDto.setCarbo(totalCarbo);
        return totalDto;
    }

    /** PFC目標値設定 */
    private void setGoal(Integer goalCalorie, Integer pfc, EatingRecordDto dto) {
        EatingRecordDto.GoalDto goalDto = new EatingRecordDto.GoalDto();

        if (goalCalorie == null) {
            goalDto.setCalories(0);
            goalDto.setProtein(0);
            goalDto.setFat(0);
            goalDto.setCarbo(0);
            dto.setGoal(goalDto);
            return;
        }

        goalDto.setCalories(goalCalorie);
        if (pfc == 0) {
            goalDto.setProtein(Math.round(goalCalorie * 0.4 / 4));
            goalDto.setFat(Math.round(goalCalorie * 0.2 / 9));
            goalDto.setCarbo(Math.round(goalCalorie * 0.4 / 4));
        } else if (pfc == 1) {
            goalDto.setProtein(Math.round(goalCalorie * 0.3 / 4));
            goalDto.setFat(Math.round(goalCalorie * 0.2 / 9));
            goalDto.setCarbo(Math.round(goalCalorie * 0.5 / 4));
        } else if (pfc == 2) {
            goalDto.setProtein(Math.round(goalCalorie * 0.55 / 4));
            goalDto.setFat(Math.round(goalCalorie * 0.25 / 9));
            goalDto.setCarbo(Math.round(goalCalorie * 0.2 / 4));
        }

        dto.setGoal(goalDto);
    }

    /** 目標達成率設定 */
    private void setRate(EatingRecordDto dto) {
        try {
            EatingRecordDto.RateDto rateDto = new EatingRecordDto.RateDto();
            EatingRecordDto.TotalDto totalDto = dto.getTotal();
            EatingRecordDto.GoalDto goalDto = dto.getGoal();

            double totalProtein = totalDto.getProtein();
            double totalFat = totalDto.getFat();
            double totalCarbo = totalDto.getCarbo();

            double goalProtein = goalDto.getProtein();
            double goalFat = goalDto.getFat();
            double goalCarbo = goalDto.getCarbo();

            double protein = totalProtein / goalProtein;
            double fat = totalFat / goalFat;
            double carbo = totalCarbo / goalCarbo;

            if (goalProtein <= 0) {
                rateDto.setProtein(0);
            } else {
                rateDto.setProtein(protein > 1 ? 1 : (Double.isNaN(protein) ? 0 : protein));
            }

            if (goalFat <= 0) {
                rateDto.setFat(0);
            } else {
                rateDto.setFat(fat > 1 ? 1 : (Double.isNaN(fat) ? 0 : fat));
            }

            if (goalCarbo <= 0) {
                rateDto.setCarbo(0);
            } else {
                rateDto.setCarbo(carbo > 1 ? 1 : (Double.isNaN(carbo) ? 0 : carbo));
            }

            dto.setRate(rateDto);
        } catch (NullPointerException e) {
            return;
        }
    }
}
