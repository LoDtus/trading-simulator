package com.trading_simulator.backend.object.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "notification")
public class Notification {
    @Id
    private String id;
    private String title;
    private String content;
    private String rootUrl;
    private String image;

    private List<String> recipient; // null tức là system, còn nếu đã là thông báo thì số người phải trên 1 người
    private List<String> read;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant sendAt;
}
