package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.dto.BodyPartDto;
import com.chibitaka.tremane_backend.service.BodyPartService;

import lombok.RequiredArgsConstructor;

/** トレーニング部位用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bodyparts")
public class BodyPartController {

    private final BodyPartService bodyPartService; // トレーニング部位Service

    /** トレーニング部位一覧取得 */
    @GetMapping("")
    public ResponseEntity<List<BodyPartDto>> getBodyParts(@RequestParam LocalDateTime updatedAt) {
        List<BodyPartDto> bodyPartDtos = bodyPartService.getBodyParts(updatedAt);

        return ResponseEntity.status(HttpStatus.OK).body(bodyPartDtos);
    }
}
