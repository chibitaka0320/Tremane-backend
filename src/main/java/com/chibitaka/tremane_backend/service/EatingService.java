package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.EatingDto;
import com.chibitaka.tremane_backend.entity.EatingEntity;
import com.chibitaka.tremane_backend.form.EatingForm;
import com.chibitaka.tremane_backend.repository.EatingRepository;

import lombok.RequiredArgsConstructor;

/** 食事関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class EatingService {

    private final EatingRepository eatingRepository; // 食事Repository
    private final ModelMapper modelMapper; // ModelMapper

    /** 食事更新情報取得 */
    public List<EatingDto> getEatingsByUserId(String userId, LocalDateTime updatedAt) {
        List<EatingEntity> eatingEntities = eatingRepository.findByUserId(userId, updatedAt);
        List<EatingDto> eatingDtos = new ArrayList<>();

        for (EatingEntity eatingEntity : eatingEntities) {
            EatingDto eatingDto = modelMapper.map(eatingEntity, EatingDto.class);
            eatingDtos.add(eatingDto);
        }

        return eatingDtos;
    }

    /** 食事詳細取得 */
    public EatingDto getEatingById(String eatingId) {
        EatingEntity eatingEntity = eatingRepository.findById(eatingId);
        EatingDto eatingDto = modelMapper.map(eatingEntity, EatingDto.class);

        return eatingDto;
    }

    /** 食事追加更新 */
    public void saveEatings(String userId, EatingForm[] forms) {

        for (EatingForm form : forms) {
            EatingEntity eatingEntity = modelMapper.map(form, EatingEntity.class);
            eatingEntity.setUserId(userId);

            eatingRepository.upsert(eatingEntity);
        }
    }

    /** 食事削除 */
    public void deleteEating(String userId, String eatingId) {
        eatingRepository.delete(userId, eatingId);
    }
}
