package com.chibitaka.tremane_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.ExerciseDto;
import com.chibitaka.tremane_backend.form.ExerciseForm;
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

    /** マイ種目リスト取得 */
    @GetMapping("/myself")
    public ResponseEntity<List<ExerciseDto>> getMyExercises(@RequestParam LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();

        List<ExerciseDto> exerciseDtos = exerciseService.getMyExerciseList(userId, updatedAt);
        return ResponseEntity.ok(exerciseDtos);
    }

    /** マイ種目追加 */
    @PostMapping("/myself")
    public ResponseEntity<Void> postMyExercises(@RequestBody ExerciseForm[] forms) {
        String userId = UserInfo.getUserId();

        exerciseService.upsertMyExercise(userId, forms);
        return ResponseEntity.status(204).build();
    }

    /** マイ種目削除 */
    @DeleteMapping("/myself/{exerciseId}")
    public ResponseEntity<Void> deleteMyExercises(@PathVariable String exerciseId) {
        String userId = UserInfo.getUserId();

        exerciseService.deleteMyExercises(userId, exerciseId);
        return ResponseEntity.status(204).build();
    }

}
