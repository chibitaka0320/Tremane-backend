package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.BodyPartDto;
import com.chibitaka.tremane_backend.dto.BodyPartExerciseDto;
import com.chibitaka.tremane_backend.entity.BodyPartEntity;
import com.chibitaka.tremane_backend.mapper.BodyPartMapper;
import com.chibitaka.tremane_backend.repository.BodyPartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BodyPartService {

    private final BodyPartRepository bodyPartRepository;

    public List<BodyPartExerciseDto> getBodyPartList() {
        List<BodyPartEntity> bodyPartEntity = bodyPartRepository.getBodyPartExerciseList();
        return BodyPartMapper.toDtoList(bodyPartEntity);
    }

    /** 部位更新情報取得 */
    public List<BodyPartDto> getBodyPartList(LocalDateTime updatedAt) {
        List<BodyPartDto> bodyPartDtos = bodyPartRepository.getBodyPart(updatedAt);
        return bodyPartDtos;
    }
}
