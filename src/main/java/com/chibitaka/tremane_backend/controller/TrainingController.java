package com.chibitaka.tremane_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.TrainingDto;
import com.chibitaka.tremane_backend.dto.TrainingRecordDto;
import com.chibitaka.tremane_backend.dto.response.TrainingResponseDto;
import com.chibitaka.tremane_backend.form.TrainingForm;
import com.chibitaka.tremane_backend.service.TrainingService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

    private final TrainingService trainingService;

    /*
     * ユーザーの日別トレーニング情報取得
     */
    @GetMapping("")
    public ResponseEntity<List<TrainingRecordDto>> getTrainings(@RequestParam LocalDate date) {
        String userId = UserInfo.getUserId();

        List<TrainingRecordDto> dto = trainingService.getTrainings(userId, date);
        return ResponseEntity.ok(dto);
    }

    /**
     * ユーザートレーニング更新情報取得
     */
    @GetMapping("/sync")
    public ResponseEntity<List<TrainingDto>> getUpdateTraining(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        List<TrainingDto> dtos = trainingService.getUpdateTrainings(userId, updatedAt);
        return ResponseEntity.ok(dtos);
    }

    /*
     * 個別トレーニング情報
     */
    @GetMapping("/{trainingId}")
    public ResponseEntity<TrainingResponseDto> getTraining(@PathVariable String trainingId) {
        TrainingResponseDto dto = trainingService.getTraining(trainingId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("")
    public ResponseEntity<Void> postTraining(@RequestBody TrainingForm[] form) {
        String userId = UserInfo.getUserId();

        trainingService.upsertTraining(userId, form);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> deleteTraining(@PathVariable String trainingId) {
        String userId = UserInfo.getUserId();

        trainingService.deleteTraining(userId, trainingId);
        return ResponseEntity.status(204).build();
    }

}
