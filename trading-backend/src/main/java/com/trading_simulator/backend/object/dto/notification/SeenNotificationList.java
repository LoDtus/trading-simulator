package com.trading_simulator.backend.object.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeenNotificationList {
    private List<String> ids;
    private Boolean all;
}