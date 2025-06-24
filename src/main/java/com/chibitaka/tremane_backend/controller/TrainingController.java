package com.chibitaka.tremane_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.TrainingRecordDto;
import com.chibitaka.tremane_backend.form.TrainingForm;
import com.chibitaka.tremane_backend.service.TrainingService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<TrainingRecordDto>> getTraining(@RequestParam LocalDate date) {
        Long userId = UserInfo.getUserId();

        List<TrainingRecordDto> dto = trainingService.getTraining(userId, date);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> postTraining(@RequestBody TrainingForm form) {
        Long userId = UserInfo.getUserId();

        trainingService.addTraining(userId, form);
        return ResponseEntity.status(204).build();
    }

}
