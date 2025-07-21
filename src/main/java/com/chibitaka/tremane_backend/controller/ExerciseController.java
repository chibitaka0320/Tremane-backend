package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.dto.ExerciseDto;
import com.chibitaka.tremane_backend.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("")
    public ResponseEntity<List<ExerciseDto>> getExercises(@RequestParam LocalDateTime updatedAt) {
        List<ExerciseDto> exerciseDtos = exerciseService.getBodyPartList(updatedAt);
        return ResponseEntity.ok(exerciseDtos);
    }
}
