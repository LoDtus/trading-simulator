package com.trading_simulator.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
@Tag(name = "Support", description = "Các API tương tác với chức năng hỗ trợ người dùng")
public class SupportController {

    @PostMapping("/get-topics")
    public ResponseEntity<?> getTopics() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/get-qa")
    public ResponseEntity<?> getQA() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/add-topic")
    public ResponseEntity<?> addTopic() {

        return ResponseEntity.ok("");
    }

    @PostMapping("/ask")
    public ResponseEntity<?> addQuestion() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/update-topic")
    public ResponseEntity<?> updateTopic() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/delete-topics")
    public ResponseEntity<?> deleteTopics(@RequestParam List<String> ids) {
        return ResponseEntity.ok("");
    }
}
