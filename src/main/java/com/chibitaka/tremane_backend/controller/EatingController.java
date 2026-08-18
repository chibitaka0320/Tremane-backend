package com.chibitaka.tremane_backend.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.EatingDto;
import com.chibitaka.tremane_backend.form.EatingForm;
import com.chibitaka.tremane_backend.service.EatingService;

import lombok.RequiredArgsConstructor;

/** 食事用Controller */
@RestController
@RequiredArgsConstructor
@RequestMapping("/eating")
public class EatingController {

    private final EatingService eatingService; // 食事Service

    /** ユーザー食事更新情報取得 */
    @GetMapping("")
    public ResponseEntity<List<EatingDto>> getEatings(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        List<EatingDto> eatingDtos = eatingService.getEatingsByUserId(userId, updatedAt);

        return ResponseEntity.status(HttpStatus.OK).body(eatingDtos);
    }

    /** 食事詳細取得 */
    @GetMapping("/{eatingId}")
    public ResponseEntity<EatingDto> getEatingDetail(@PathVariable String eatingId) {
        EatingDto eatingDto = eatingService.getEatingById(eatingId);

        return ResponseEntity.status(HttpStatus.OK).body(eatingDto);
    }

    /** 食事追加更新 */
    @PostMapping("")
    public ResponseEntity<Void> saveEatings(@RequestBody EatingForm[] forms) {
        String userId = UserInfo.getUserId();
        eatingService.saveEatings(userId, forms);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /** 食事削除 */
    @DeleteMapping("/{eatingId}")
    public ResponseEntity<Void> deleteEating(@PathVariable String eatingId) {
        String userId = UserInfo.getUserId();
        eatingService.deleteEating(userId, eatingId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
