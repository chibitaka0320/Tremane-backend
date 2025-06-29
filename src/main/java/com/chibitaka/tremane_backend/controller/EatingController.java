package com.chibitaka.tremane_backend.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chibitaka.tremane_backend.common.util.UserInfo;
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
    public ResponseEntity<EatingRecordDto> getEating(@RequestParam LocalDate date) {
        Long userId = UserInfo.getUserId();
        EatingRecordDto eatingRecordDto = eatingService.getEating(userId, date);
        return ResponseEntity.ok(eatingRecordDto);
    }

    /** 食事記録追加 */
    @PostMapping("")
    public ResponseEntity<Void> postEating(@RequestBody EatingForm form) {
        Long userId = UserInfo.getUserId();

        eatingService.addEating(userId, form);
        return ResponseEntity.status(204).build();
    }
}
