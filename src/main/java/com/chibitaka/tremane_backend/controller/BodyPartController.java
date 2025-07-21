package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.dto.BodyPartDto;
import com.chibitaka.tremane_backend.dto.BodyPartExerciseDto;
import com.chibitaka.tremane_backend.service.BodyPartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bodyparts")
public class BodyPartController {

    private final BodyPartService bodyPartService;

    @GetMapping("")
    public ResponseEntity<List<BodyPartExerciseDto>> getBodyPartExerciseList() {
        List<BodyPartExerciseDto> bodyPartExerciseDto = bodyPartService.getBodyPartList();
        return ResponseEntity.ok(bodyPartExerciseDto);
    }

    @GetMapping("/sync")
    public ResponseEntity<List<BodyPartDto>> getBodyParts(@RequestParam LocalDateTime updatedAt) {
        List<BodyPartDto> bodyPartDtos = bodyPartService.getBodyPartList(updatedAt);
        return ResponseEntity.ok(bodyPartDtos);
    }
}
