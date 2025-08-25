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
@Document(collection = "support_qa")
public class SupportQA {
    @Id
    private String id;
    private String topic;
    private String sender;
    private String content;
    private String replyId;

    private Instant createdAt;
    private Instant updatedAt;
}
