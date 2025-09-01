package com.trading_simulator.backend.config.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {
    private final ConcurrentHashMap<String, Set<String>> userSessionMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleSessionConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = accessor.getUser() != null ? accessor.getUser().getName() : null;
        String sessionId = accessor.getSessionId();
        if (userId != null && sessionId != null) {
            userSessionMap.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
            System.out.println("Stored mapping: userId=" + userId + ", sessionId=" + sessionId);
        }
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = accessor.getUser() != null ? accessor.getUser().getName() : null;
        String sessionId = accessor.getSessionId();
        if (userId != null && sessionId != null) {
            Set<String> sessions = userSessionMap.get(userId);
            if (sessions != null) {
                sessions.remove(sessionId);
                if (sessions.isEmpty()) {
                    userSessionMap.remove(userId);
                }
                System.out.println("Removed mapping: userId=" + userId + ", sessionId=" + sessionId);
            }
        }
    }
}