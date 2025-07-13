package com.chibitaka.tremane_backend.controller;

import java.time.LocalDate;

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

    /** 食事記録詳細取得 */
    @GetMapping("/{eatingId}")
    public ResponseEntity<EatingDto> getEating(@PathVariable long eatingId) {
        EatingDto dto = eatingService.getEating(eatingId);
        return ResponseEntity.ok(dto);
    }

    /** 食事記録追加 */
    @PostMapping("")
    public ResponseEntity<Void> postEating(@RequestBody EatingForm form) {
        String userId = UserInfo.getUserId();

        eatingService.addEating(userId, form);
        return ResponseEntity.status(204).build();
    }

    /** 食事記録更新 */
    @PutMapping("/{eatingId}")
    public ResponseEntity<Void> updateEating(@PathVariable long eatingId, @RequestBody EatingForm form) {
        String userId = UserInfo.getUserId();

        eatingService.updateEating(userId, eatingId, form);
        return ResponseEntity.status(204).build();
    }

    /** 食事記録削除 */
    @DeleteMapping("/{eatingId}")
    public ResponseEntity<Void> deleteEating(@PathVariable long eatingId) {
        String userId = UserInfo.getUserId();

        eatingService.deleteEating(userId, eatingId);
        return ResponseEntity.status(204).build();
    }
}
