package com.chibitaka.tremane_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.dto.BodyPartDto;
import com.chibitaka.tremane_backend.entity.BodyPartEntity;
import com.chibitaka.tremane_backend.repository.BodyPartRepository;

import lombok.RequiredArgsConstructor;

/** トレーニング部位関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class BodyPartService {

    private final BodyPartRepository bodyPartRepository; // トレーニング部位Repository
    private final ModelMapper modelMapper; // ModelMapper

    /** トレーニング部位更新情報取得 */
    public List<BodyPartDto> getBodyParts(LocalDateTime updatedAt) {
        List<BodyPartEntity> bodyPartEntities = bodyPartRepository.findAll(updatedAt);
        List<BodyPartDto> bodyPartDtos = new ArrayList<>();

        for (BodyPartEntity bodyPartEntity : bodyPartEntities) {
            BodyPartDto bodyPartDto = modelMapper.map(bodyPartEntity, BodyPartDto.class);
            bodyPartDtos.add(bodyPartDto);
        }

        return bodyPartDtos;
    }
}
