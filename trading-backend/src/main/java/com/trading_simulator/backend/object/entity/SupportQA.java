package com.trading_simulator.backend.object.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank
    private String topic;

    @NotBlank
    private String sender;

    @NotBlank
    private String content;

    private String replyId;

    @NotNull
    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;

    @NotNull
    @PastOrPresent(message = "The update time cannot be in the future")
    private Instant updatedAt;
}
