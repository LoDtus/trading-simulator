package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications();
    Notification readNotification();
    void deleteNotification(List<String> ids);
}
