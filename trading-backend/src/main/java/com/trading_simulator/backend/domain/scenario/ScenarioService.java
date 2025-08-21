package com.trading_simulator.backend.domain.scenario;

import java.util.List;

public interface ScenarioService {
    List<Scenario> find();
    Scenario save(Scenario scenario);
    Boolean deleteById(String id);
}
