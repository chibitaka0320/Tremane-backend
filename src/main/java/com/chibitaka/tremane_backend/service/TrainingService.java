package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.TrainingDto;
import com.chibitaka.tremane_backend.dto.TrainingDetailDto;
import com.chibitaka.tremane_backend.entity.TrainingEntity;
import com.chibitaka.tremane_backend.form.TrainingForm;
import com.chibitaka.tremane_backend.repository.TrainingRepository;

import lombok.RequiredArgsConstructor;

/** トレーニング関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class TrainingService {

    private final TrainingRepository trainingRepository; // トレーニングRepository
    private final ModelMapper modelMapper; // ModelMapper

    /** ユーザートレーニング更新情報取得 */
    public List<TrainingDto> getTrainingsByUserId(String userId, LocalDateTime updatedAt) {
        List<TrainingEntity> trainingEntities = trainingRepository.findByUserId(userId, updatedAt);
        List<TrainingDto> trainingDtos = new ArrayList<>();

        for (TrainingEntity trainingEntity : trainingEntities) {
            TrainingDto trainingDto = modelMapper.map(trainingEntity, TrainingDto.class);
            trainingDtos.add(trainingDto);
        }

        return trainingDtos;
    }

    /** トレーニング詳細データ取得 */
    public TrainingDetailDto getTrainingById(String trainingId) {
        TrainingDetailDto trainingDetailDto = trainingRepository.findById(trainingId);
        return trainingDetailDto;
    }

    /** トレーニング追加更新 */
    public void saveTrainings(String userId, TrainingForm[] forms) {

        for (TrainingForm form : forms) {
            TrainingEntity trainingEntity = modelMapper.map(form, TrainingEntity.class);
            trainingEntity.setUserId(userId);

            trainingRepository.upsert(trainingEntity);
        }
    }

    /** トレーニング記録削除 */
    public void deleteTraining(String userId, String trainingId) {
        trainingRepository.delete(userId, trainingId);
    }

}
