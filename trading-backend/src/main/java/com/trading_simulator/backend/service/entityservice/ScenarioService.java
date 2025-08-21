package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Scenario;

import java.util.List;

public interface ScenarioService {
    List<Scenario> find();
    Scenario save(Scenario scenario);
    Boolean deleteById(String id);
}
