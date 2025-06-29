package com.chibitaka.tremane_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.EatingRecordDto;
import com.chibitaka.tremane_backend.entity.EatingEntity;
import com.chibitaka.tremane_backend.form.EatingForm;
import com.chibitaka.tremane_backend.mapper.EatingMapper;
import com.chibitaka.tremane_backend.repository.EatingRepository;

import lombok.RequiredArgsConstructor;

/** 食事記録サービスクラス */
@Service
@RequiredArgsConstructor
@Transactional
public class EatingService {

    private final EatingRepository eatingRepository;

    /** 食事記録取得 */
    public EatingRecordDto getEating(Long userId, LocalDate date) {
        // 食事記録一覧取得
        List<EatingEntity> eatings = eatingRepository.findByUserIdAndDate(userId, date);

        EatingRecordDto eatingDto = new EatingRecordDto();
        eatingDto.setDate(date);
        eatingDto.setMeals(EatingMapper.toDtoList(eatings));
        eatingDto.setTotal(calcTotal(eatings));

        return eatingDto;
    }

    /** 食事記録追加 */
    public void addEating(Long userId, EatingForm form) {
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
}
