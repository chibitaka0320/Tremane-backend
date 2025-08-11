package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.ExerciseDto;
import com.chibitaka.tremane_backend.entity.MyExerciseEntity;
import com.chibitaka.tremane_backend.form.ExerciseForm;
import com.chibitaka.tremane_backend.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    // 種目リスト取得
    public List<ExerciseDto> getBodyPartList(LocalDateTime updatedAt) {
        List<ExerciseDto> exerciseDtos = exerciseRepository.getExercise(updatedAt);
        return exerciseDtos;
    }

    // マイ種目リスト取得
    public List<ExerciseDto> getMyExerciseList(String userId, LocalDateTime updatedAt) {
        List<ExerciseDto> exerciseDtos = exerciseRepository.getMyExercise(userId, updatedAt);
        return exerciseDtos;
    }

    // マイ種目追加
    public void upsertMyExercise(String userId, ExerciseForm[] forms) {
        for (ExerciseForm form : forms) {
            MyExerciseEntity entity = new MyExerciseEntity();
            entity.setExerciseId(form.getExerciseId());
            entity.setUserId(userId);
            entity.setPartsId(form.getPartsId());
            entity.setName(form.getName());
            entity.setCreatedAt(form.getCreatedAt());
            entity.setUpdatedAt(form.getUpdatedAt());

            exerciseRepository.upsertMyExercise(entity);
        }
    }

    // マイ種目削除
    public void deleteMyExercises(String userId, String exerciseId) {
        exerciseRepository.deleteMyExercise(userId, exerciseId);
    }

}
