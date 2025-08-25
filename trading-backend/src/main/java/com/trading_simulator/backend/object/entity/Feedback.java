package com.trading_simulator.backend.object.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "feedback")
public class Feedback {
    @Id
    private String id;

    private String username; // null
    private String email; // null

    private String content;
    private String contentType;

    // rate...

    private Instant sendAt;
}
