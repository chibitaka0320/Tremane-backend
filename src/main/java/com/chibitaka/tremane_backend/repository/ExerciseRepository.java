package com.chibitaka.tremane_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chibitaka.tremane_backend.dto.ExerciseDto;

@Mapper
public interface ExerciseRepository {

    List<ExerciseDto> getExercise(LocalDateTime updatedAt);
}
