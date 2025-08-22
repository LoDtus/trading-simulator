package com.trading_simulator.backend.controller.test;

import com.trading_simulator.backend.object.dto.test.TestData;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/web-socket")
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send-single")
    public Boolean sendSingle(@RequestBody TestData testData) {
        String message = "Test Single";
        System.out.println("Data Single: " + testData.getContent());

        messagingTemplate.convertAndSendToUser(
                "user-123",
                "/test",
                message
        );
        return true;
    }

    // gud
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
