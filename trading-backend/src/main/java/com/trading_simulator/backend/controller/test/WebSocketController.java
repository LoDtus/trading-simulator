package com.trading_simulator.backend.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/test/web-socket")
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send-single")
    public void sendSingle(com.trading_simulator.backend.controller.test.TestData testData, Principal principal) {
        String message = "Test Single";
        String userId = principal != null ? principal.getName() : (testData.getUserId() != null ? testData.getUserId() : "user-123");
        System.out.println("Data Single: " + testData.getContent());
        System.out.println("Sending to userId=" + userId + " on /user/" + userId + "/test, principal=" + (principal != null ? principal.getName() : "null"));

        messagingTemplate.convertAndSendToUser(
                userId,
                "/test",
                message
        );
    }

    @PostMapping("/send-global")
    public Boolean sendGlobal(@RequestBody TestData testData) {
        String message = "Test Global";
        System.out.println("Data Global: " + testData.getContent());
        messagingTemplate.convertAndSend(
                "/public/test/123",
                message
        );
        return true;
    }
}