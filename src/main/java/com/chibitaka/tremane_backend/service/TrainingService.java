package com.chibitaka.tremane_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.TrainingRecordDto;
import com.chibitaka.tremane_backend.entity.TrainingEntity;
import com.chibitaka.tremane_backend.entity.TrainingRecordEntity;
import com.chibitaka.tremane_backend.mapper.TrainingRecordMapper;
import com.chibitaka.tremane_backend.repository.TrainingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public List<TrainingRecordDto> getTraining(Long userId, LocalDate date) {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setUserId(1L);
        trainingEntity.setDate(date);

        List<TrainingRecordEntity> recordEntities = trainingRepository.findByUserIdAndDate(trainingEntity);

        return TrainingRecordMapper.toDtoList(recordEntities);
    }

}
