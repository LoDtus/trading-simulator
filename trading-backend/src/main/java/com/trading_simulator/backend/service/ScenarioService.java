package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.dto.scenario.UpdateScenario;
import com.trading_simulator.backend.object.entity.Scenario;

import java.util.List;

public interface ScenarioService {
    List<Scenario> getScenarios();
    Scenario addScenario(Scenario scenario);
    Scenario updateScenario(UpdateScenario updateScenario);
    void deleteByIds(List<String> ids);
    void deleteByOwners(List<String> owners);
}
