package com.trading_simulator.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
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

    @PutMapping("/update")
    public ResponseEntity<?> updateTopic() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTopics(@RequestParam List<String> ids) {
        return ResponseEntity.ok("");
    }
}
