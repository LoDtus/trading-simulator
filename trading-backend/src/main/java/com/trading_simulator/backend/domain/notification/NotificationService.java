package com.trading_simulator.backend.domain.notification;

public interface NotificationService {
    Notification findById(String id);
    Notification save(Notification notification);
    Boolean deleteById(String id);
}
