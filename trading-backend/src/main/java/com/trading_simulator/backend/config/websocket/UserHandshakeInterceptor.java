package com.trading_simulator.backend.config.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;

public class UserHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes
    ) throws Exception {
//        if (request instanceof ServletServerHttpRequest servletRequest) {
//            String userId = servletRequest.getServletRequest().getParameter("user");
//            if (userId != null && !userId.isEmpty()) {
//                attributes.put("userId", userId);
//                System.out.println(">>> WebSocket Handshake OK. userId=" + userId);
//            } else {
//                System.out.println(">>> WebSocket Handshake FAILED. No userId found.");
//                return false;
//            }
//        }
//        return true; // cho phép handshake tiếp tục

        if (request instanceof ServletServerHttpRequest servletRequest) {
            String userId = servletRequest.getServletRequest().getParameter("user");
            String sessionId = request.getHeaders().getFirst("sec-websocket-key");
            if (userId != null && !userId.isEmpty()) {
                attributes.put("userId", userId);
                System.out.println(">>> WebSocket Handshake OK. userId=" + userId + ", sessionId=" + sessionId);
                return true;
            } else {
                System.out.println(">>> WebSocket Handshake FAILED. No userId found, sessionId=" + sessionId);
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception
    ) {
        if (exception != null) {
            System.out.println(">>> WebSocket Handshake error: " + exception.getMessage());
        }
    }
}
