package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.ScenarioStatus;
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

//    @NotBlank(message = "Owner is required")
    private String owner;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer pin;

    private ScenarioStatus status;
}
