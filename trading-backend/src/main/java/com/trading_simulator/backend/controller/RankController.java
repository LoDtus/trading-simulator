package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.domain.rank.RankService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
@Tag(name = "Rank", description = "Các API tương tác với xếp hạng người dùng")
public class RankController {
    private final RankService rankService;

    @PostMapping("/get")
    public ResponseEntity<?> getRank() {
        return ResponseEntity.ok("");
    }
}
