package com.chibitaka.tremane_backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
import com.chibitaka.tremane_backend.dto.EatingDto;
import com.chibitaka.tremane_backend.dto.EatingRecordDto;
import com.chibitaka.tremane_backend.form.EatingForm;
import com.chibitaka.tremane_backend.service.EatingService;

import lombok.RequiredArgsConstructor;

/**
 * 食事用コントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/eating")
public class EatingController {

    private final EatingService eatingService;

    @GetMapping("")
    public ResponseEntity<EatingRecordDto> getEatings(@RequestParam LocalDate date) {
        String userId = UserInfo.getUserId();
        EatingRecordDto eatingRecordDto = eatingService.getEatings(userId, date);
        return ResponseEntity.ok(eatingRecordDto);
    }

    /** ユーザー食事記録更新情報取得 */
    @GetMapping("/sync")
    public ResponseEntity<List<EatingDto>> getUpdateEating(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedAt) {
        String userId = UserInfo.getUserId();
        List<EatingDto> dtos = eatingService.getUpdateEatings(userId, updatedAt);
        return ResponseEntity.ok(dtos);
    }

    /** 食事記録詳細取得 */
    @GetMapping("/{eatingId}")
    public ResponseEntity<EatingDto> getEating(@PathVariable String eatingId) {
        EatingDto dto = eatingService.getEating(eatingId);
        return ResponseEntity.ok(dto);
    }

    /** 食事記録追加 */
    @PostMapping("")
    public ResponseEntity<Void> postEating(@RequestBody EatingForm[] forms) {
        String userId = UserInfo.getUserId();

        eatingService.addEating(userId, forms);
        return ResponseEntity.status(204).build();
    }

    /** 食事記録更新 */
    @PutMapping("/{eatingId}")
    public ResponseEntity<Void> updateEating(@PathVariable String eatingId, @RequestBody EatingForm form) {
        String userId = UserInfo.getUserId();

        eatingService.updateEating(userId, eatingId, form);
        return ResponseEntity.status(204).build();
    }

    /** 食事記録削除 */
    @DeleteMapping("/{eatingId}")
    public ResponseEntity<Void> deleteEating(@PathVariable String eatingId) {
        String userId = UserInfo.getUserId();

        eatingService.deleteEating(userId, eatingId);
        return ResponseEntity.status(204).build();
    }
}
