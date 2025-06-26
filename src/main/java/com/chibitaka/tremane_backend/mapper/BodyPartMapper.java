
package com.chibitaka.tremane_backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.chibitaka.tremane_backend.dto.BodyPartExerciseDto;
import com.chibitaka.tremane_backend.entity.BodyPartEntity;

public class BodyPartMapper {

    public static BodyPartExerciseDto toDto(BodyPartEntity entity) {
        BodyPartExerciseDto dto = new BodyPartExerciseDto();
        dto.setPartsId(entity.getPartsId());
        dto.setName(entity.getName());
        dto.setExercises(
                Optional.ofNullable(entity.getExercises().stream()
                        .map(ex -> {
                            BodyPartExerciseDto.ExerciseDto exDto = new BodyPartExerciseDto.ExerciseDto();
                            exDto.setExerciseId(ex.getExerciseId());
                            exDto.setName(ex.getName());
                            return exDto;
                        })
                        .collect(Collectors.toList())).orElse(Collections.emptyList()));
        return dto;
    }

    public static List<BodyPartExerciseDto> toDtoList(List<BodyPartEntity> entities) {
        return entities.stream()
                .map(BodyPartMapper::toDto)
                .collect(Collectors.toList());
    }
}
