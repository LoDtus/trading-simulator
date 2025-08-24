package com.trading_simulator.backend.domain.scenario;

import com.trading_simulator.backend.config.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {
    private final ScenarioRepository scenarioRepository;

    @Override
    public List<Scenario> getScenarios() {
        return List.of();
    }

    @Override
    public Scenario addScenario() {
        return null;
    }

    @Override
    public Scenario updateScenario() {
        return null;
    }

    @Override
    public void deleteScenarios() {

    }
}
