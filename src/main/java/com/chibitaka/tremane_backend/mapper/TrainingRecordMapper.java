package com.chibitaka.tremane_backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.chibitaka.tremane_backend.dto.TrainingRecordDto;
import com.chibitaka.tremane_backend.entity.TrainingRecordEntity;

public class TrainingRecordMapper {

    public static TrainingRecordDto toDto(TrainingRecordEntity entity) {
        TrainingRecordDto dto = new TrainingRecordDto();
        dto.setName(entity.getName());
        dto.setExercises(
                entity.getExercises().stream()
                        .map(ex -> {
                            TrainingRecordDto.Exercise exDto = new TrainingRecordDto.Exercise();
                            exDto.setName(ex.getName());
                            exDto.setSets(
                                    ex.getSets().stream()
                                            .map(set -> {
                                                TrainingRecordDto.Set setDto = new TrainingRecordDto.Set();
                                                setDto.setTrainingId(set.getTrainingId());
                                                setDto.setWeight(set.getWeight());
                                                setDto.setReps(set.getReps());
                                                return setDto;
                                            })
                                            .collect(Collectors.toList()));
                            return exDto;
                        })
                        .collect(Collectors.toList()));
        return dto;
    }

    public static List<TrainingRecordDto> toDtoList(List<TrainingRecordEntity> entities) {
        return entities.stream()
                .map(TrainingRecordMapper::toDto)
                .collect(Collectors.toList());
    }
}
