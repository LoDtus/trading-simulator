package com.trading_simulator.backend.config.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
//        String userId = (String) attributes.get("userId");
//        if (userId != null) {
//            System.out.println(">>> Determined user: " + userId);
//            return () -> userId;
//        }
//        System.out.println(">>> No userId found, returning null Principal");
//        return null;

        String userId = (String) attributes.get("userId");
        String sessionId = request.getHeaders().getFirst("sec-websocket-key");
        if (userId != null) {
            System.out.println(">>> Determined user: " + userId + ", sessionId: " + sessionId);
            return () -> userId;
        }
        System.out.println(">>> No userId found, returning null Principal, sessionId: " + sessionId);
        return null;
    }
}
