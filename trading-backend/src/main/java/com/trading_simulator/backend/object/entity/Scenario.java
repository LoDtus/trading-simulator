package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.ScenarioStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "scenario")
public class Scenario { // dòng thời gian
    @Id
    private String id;

    @NotBlank(message = "Owner is required")
    private String owner;

    @NotBlank(message = "Scenario name is required")
    private String name;
    private String description;

    @NotNull(message = "Time of creation name is required")
    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;

    @NotNull(message = "Time of update name is required")
    @PastOrPresent(message = "The update time cannot be in the future")
    private Instant updatedAt;

    @Min(0) @Max(50)
    private Integer pin;

    private ScenarioStatus status;
}
