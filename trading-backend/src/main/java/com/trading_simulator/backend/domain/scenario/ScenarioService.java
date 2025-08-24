package com.trading_simulator.backend.domain.scenario;

import java.util.List;

public interface ScenarioService {
    List<Scenario> getScenarios();
    Scenario addScenario();
    Scenario updateScenario();
    void deleteScenarios();
}
