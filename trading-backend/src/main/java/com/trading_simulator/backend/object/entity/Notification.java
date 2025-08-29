package com.trading_simulator.backend.object.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String rootUrl;
    private String image;

    private List<String> recipient; // null tức là system, còn nếu đã là thông báo thì số người phải trên 1 người
    private List<String> read;

    @NotNull
    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;

    @NotNull
    @PastOrPresent(message = "The update time cannot be in the future")
    private Instant updatedAt;

    @NotNull
    @PastOrPresent(message = "The send time cannot be in the future")
    private Instant sendAt;
}
