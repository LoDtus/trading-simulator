package com.trading_simulator.backend.domain.notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications();
    Notification readNotification();
    void deleteNotification(List<String> ids);
}
