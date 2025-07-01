package com.chibitaka.tremane_backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.chibitaka.tremane_backend.dto.EatingDto;
import com.chibitaka.tremane_backend.entity.EatingEntity;

public class EatingMapper {
    public static EatingDto toDto(EatingEntity entity) {
        EatingDto dto = new EatingDto();
        dto.setEatingId(entity.getEatingId());
        dto.setName(entity.getName());
        dto.setCalories(entity.getCalories());
        dto.setProtein(entity.getProtein());
        dto.setFat(entity.getFat());
        dto.setCarbo(entity.getCarbo());
        return dto;
    }

    public static List<EatingDto> toDtoList(List<EatingEntity> entities) {
        return entities.stream()
                .map(EatingMapper::toDto)
                .collect(Collectors.toList());
    }
}
