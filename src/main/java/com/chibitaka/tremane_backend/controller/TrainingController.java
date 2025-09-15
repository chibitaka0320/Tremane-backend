package com.chibitaka.tremane_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.TrainingDto;
import com.chibitaka.tremane_backend.dto.TrainingDetailDto;
import com.chibitaka.tremane_backend.form.TrainingForm;
import com.chibitaka.tremane_backend.service.TrainingService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * トレーニング用コントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService; // トレーニングService

    /** ユーザートレーニング更新情報取得 */
    @GetMapping("")
    public ResponseEntity<List<TrainingDto>> getTrainings(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        List<TrainingDto> dtos = trainingService.getTrainingsByUserId(userId,
                updatedAt);
        return ResponseEntity.ok(dtos);
    }

    /** トレーニング詳細取得 */
    @GetMapping("/{trainingId}")
    public ResponseEntity<TrainingDetailDto> getTrainingDetail(@PathVariable String trainingId) {
        TrainingDetailDto trainingDetailDto = trainingService.getTrainingById(trainingId);

        return ResponseEntity.status(HttpStatus.OK).body(trainingDetailDto);
    }

    /** トレーニング追加更新 */
    @PostMapping("")
    public ResponseEntity<Void> saveTrainings(@RequestBody TrainingForm[] forms) {
        String userId = UserInfo.getUserId();
        trainingService.saveTrainings(userId, forms);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** トレーニング削除 */
    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> deleteTraining(@PathVariable String trainingId) {
        String userId = UserInfo.getUserId();
        trainingService.deleteTraining(userId, trainingId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
