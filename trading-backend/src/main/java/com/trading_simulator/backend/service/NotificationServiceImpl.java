package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.Notification;
import com.trading_simulator.backend.object.entity.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotifications() {
        return List.of();
    }

    @Override
    public Notification readNotification() {
        return null;
    }

    @Override
    public void deleteNotification(List<String> ids) {

    }
}
