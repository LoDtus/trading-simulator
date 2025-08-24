package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.domain.notification.Notification;
import com.trading_simulator.backend.domain.notification.NotificationService;
import com.trading_simulator.backend.object.dto.notification.SeenNotificationList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Các API tương tác với thông báo trong ứng dụng")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/get")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNotifications() {
        return ResponseEntity.ok("");
    }

    @PutMapping("/seen")
    public ResponseEntity<?> readNotification(
            HttpServletRequest request,
            @RequestBody SeenNotificationList notificationList
    ) {
//        String userId = "";
//        if (notificationList.getAll()) {
//
//        }
//
//        List<String> deletedNotification = new ArrayList<>(List.of());
//        for (String id : notificationList.getIds()) {
//            Notification notification = notificationService.findById(id);
//            if (notification == null) {
//                deletedNotification.add(id);
//            }
//            notification = notification.toBuilder()
//
//                    .build();
//            notification = notificationService.save(notification);
//        }
        return ResponseEntity.ok("");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateNotification(
            @RequestBody Notification notification
    ) {
        // chỉ được update đối với những noti chưa được gửi
        return ResponseEntity.ok("");
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteNotifications(@RequestParam List<String> ids) {
        return ResponseEntity.ok("");
    }
}
