package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.service.entityservice.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    @PostMapping("/get")
    public ResponseEntity<?> getRank() {
        return ResponseEntity.ok("");
    }
}
