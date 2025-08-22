package com.trading_simulator.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Các API tương tác với thông báo trong ứng dụng")
public class NotificationController {
    @PostMapping("/get")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNotifications() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateNotification() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteNotifications(@RequestParam List<String> ids) {
        return ResponseEntity.ok("");
    }
}
