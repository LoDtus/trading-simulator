package com.trading_simulator.backend.object.entity;

import jakarta.validation.constraints.Email;
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
@Document(collection = "feedback")
public class Feedback {
    @Id
    private String id;

    private String username; // null

    @Email(message = "Email invalid")
    private String email; // null

    @NotBlank
    private String content;
    private String contentType;

    // rate...

    @NotNull
    @PastOrPresent(message = "The send time cannot be in the future")
    private Instant sendAt;
}
