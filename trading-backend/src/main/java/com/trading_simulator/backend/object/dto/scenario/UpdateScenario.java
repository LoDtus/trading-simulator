package com.trading_simulator.backend.object.dto.scenario;

import com.trading_simulator.backend.common.enums.ScenarioStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateScenario {
    private String id;
    private String name;
    private String description;

    @Min(0) @Max(50)
    private Integer pin;
    private ScenarioStatus status;
}
