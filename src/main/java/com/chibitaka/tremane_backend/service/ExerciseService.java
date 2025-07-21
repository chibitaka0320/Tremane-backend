package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.ExerciseDto;
import com.chibitaka.tremane_backend.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public List<ExerciseDto> getBodyPartList(LocalDateTime updatedAt) {
        List<ExerciseDto> exerciseDtos = exerciseRepository.getExercise(updatedAt);
        return exerciseDtos;
    }
}
