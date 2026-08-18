package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.ExerciseDto;
import com.chibitaka.tremane_backend.entity.ExerciseEntity;
import com.chibitaka.tremane_backend.form.ExerciseForm;
import com.chibitaka.tremane_backend.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

/** トレーニング種目関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository; // トレーニング種目Repository
    private final ModelMapper modelMapper; // ModelMapper

    // トレーニング種目一覧取得(システム)
    public List<ExerciseDto> getExercisesBySystem(LocalDateTime updatedAt) {
        List<ExerciseEntity> exerciseEntities = exerciseRepository.findBySystemUser(updatedAt);
        List<ExerciseDto> exerciseDtos = new ArrayList<>();

        for (ExerciseEntity exerciseEntity : exerciseEntities) {
            ExerciseDto exerciseDto = modelMapper.map(exerciseEntity, ExerciseDto.class);
            exerciseDtos.add(exerciseDto);
        }

        return exerciseDtos;
    }

    // マイトレーニング種目一覧取得
    public List<ExerciseDto> getExercisesByUserId(String userId, LocalDateTime updatedAt) {
        List<ExerciseEntity> exerciseEntities = exerciseRepository.findByUserId(userId, updatedAt);
        List<ExerciseDto> exerciseDtos = new ArrayList<>();

        for (ExerciseEntity exerciseEntity : exerciseEntities) {
            ExerciseDto exerciseDto = modelMapper.map(exerciseEntity, ExerciseDto.class);
            exerciseDtos.add(exerciseDto);
        }

        return exerciseDtos;
    }

    // マイトレーニング種目追加更新
    public void saveMyExercises(String userId, ExerciseForm[] forms) {
        for (ExerciseForm form : forms) {
            ExerciseEntity entity = modelMapper.map(form, ExerciseEntity.class);
            entity.setOwnerUserId(userId);

            exerciseRepository.upsertMyExercise(entity);
        }
    }

    // マイトレーニング種目削除
    public void deleteMyExercise(String userId, String exerciseId) {
        exerciseRepository.deleteMyExercise(userId, exerciseId);
    }

}
