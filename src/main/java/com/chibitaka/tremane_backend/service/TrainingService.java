package com.chibitaka.tremane_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.TrainingDto;
import com.chibitaka.tremane_backend.dto.TrainingRecordDto;
import com.chibitaka.tremane_backend.dto.response.TrainingResponseDto;
import com.chibitaka.tremane_backend.entity.TrainingEntity;
import com.chibitaka.tremane_backend.entity.TrainingRecordEntity;
import com.chibitaka.tremane_backend.form.TrainingForm;
import com.chibitaka.tremane_backend.mapper.TrainingRecordMapper;
import com.chibitaka.tremane_backend.repository.TrainingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public List<TrainingDto> getUpdateTrainings(String userId, LocalDateTime updatedAt) {
        List<TrainingDto> trainingDtos = trainingRepository.getTrainings(userId, updatedAt);
        return trainingDtos;
    }

    public List<TrainingRecordDto> getTrainings(String userId, LocalDate date) {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setUserId(userId);
        trainingEntity.setDate(date);

        List<TrainingRecordEntity> recordEntities = trainingRepository.findByUserIdAndDate(trainingEntity);

        return TrainingRecordMapper.toDtoList(recordEntities);
    }

    /** トレーニング詳細データ取得 */
    public TrainingResponseDto getTraining(long trainingId) {
        TrainingResponseDto trainingDto = trainingRepository.findById(trainingId);
        return trainingDto;
    }

    public void addTraining(String userId, TrainingForm form) {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setDate(form.getDate());
        trainingEntity.setUserId(userId);
        trainingEntity.setExerciseId(form.getExerciseId());
        trainingEntity.setWeight(form.getWeight());
        trainingEntity.setReps(form.getReps());

        trainingRepository.insertTraining(trainingEntity);
    }

    /** トレーニング記録更新 */
    public void updateTraining(String userId, long trainingId, TrainingForm form) {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setUserId(userId);
        trainingEntity.setDate(form.getDate());
        trainingEntity.setTrainingId(trainingId);
        trainingEntity.setExerciseId(form.getExerciseId());
        trainingEntity.setWeight(form.getWeight());
        trainingEntity.setReps(form.getReps());

        trainingRepository.update(trainingEntity);
    }

    /** トレーニング記録削除 */
    public void deleteTraining(String userId, long trainingId) {
        trainingRepository.delete(userId, trainingId);
    }

}
