package com.trading_simulator.backend.object.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Document(collection = "refresh_token")
public class RefreshToken {
    @Id
    private String token;

    @NotBlank
    private String owner;
    private String deviceFingerprint;

    @NotNull
    private Instant exp;
}
