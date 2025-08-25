package com.trading_simulator.backend.object.dto.scenario;

import com.trading_simulator.backend.common.enums.ScenarioStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateScenario {
    private String id;
    private String name;
    private String description;
    private Integer pin;
    private ScenarioStatus status;
}
