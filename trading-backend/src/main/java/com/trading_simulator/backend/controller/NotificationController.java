package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.object.entity.Notification;
import com.trading_simulator.backend.service.NotificationService;
import com.trading_simulator.backend.object.dto.notification.SeenNotificationList;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, Object> res = new HashMap<>();
        res.put("status", "success");
        res.put("message", "Mật khẩu đã được đặt lại");

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
