package com.trading_simulator.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Các API tương tác với góp ý của người dùng")
public class FeedbackController {
    @PostMapping("/get")
    public ResponseEntity<?> getFeedbacks() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFeedback() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFeedback() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFeedbacks() {
        return ResponseEntity.ok("");
    }
}
