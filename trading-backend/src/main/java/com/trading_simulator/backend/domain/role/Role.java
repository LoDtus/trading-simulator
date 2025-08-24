package com.trading_simulator.backend.domain.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "role")
public class Role {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private String role;

    private String description;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;
}
