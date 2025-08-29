package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.InputType;
import com.trading_simulator.backend.config.validation.ValidInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "profile")
public class Profile {
    @Id
    private String id;

    @NotBlank
    private String image;

    @NotBlank
    private String status;

    // Jsoup sanitize dùng decode → OWASP Java Encoder dùng để encode trước khi trả dữ liệu về
    private String bio;

    @Past
    private Instant dateOfBirth;

    private List<String> address; // [nation, city]

    @NotNull
    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;

    @NotNull
    @PastOrPresent(message = "The update time cannot be in the future")
    private Instant updatedAt;
}
