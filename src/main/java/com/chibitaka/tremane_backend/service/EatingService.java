package com.chibitaka.tremane_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.entity.EatingEntity;
import com.chibitaka.tremane_backend.form.EatingForm;
import com.chibitaka.tremane_backend.repository.EatingRepository;

import lombok.RequiredArgsConstructor;

/** 食事記録サービスクラス */
@Service
@RequiredArgsConstructor
@Transactional
public class EatingService {

    private final EatingRepository eatingRepository;

    /** 食事記録追加 */
    public void addEating(Long userId, EatingForm form) {
        EatingEntity entity = new EatingEntity();
        entity.setDate(form.getDate());
        entity.setUserId(userId);
        entity.setName(form.getName());
        entity.setProtein(form.getProtein());
        entity.setFat(form.getFat());
        entity.setCarbo(form.getCarbo());

        eatingRepository.insertEating(entity);
    }
}
