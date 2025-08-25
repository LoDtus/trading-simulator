package com.trading_simulator.backend.service;

import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.object.dto.scenario.UpdateScenario;
import com.trading_simulator.backend.object.entity.Auth;
import com.trading_simulator.backend.object.entity.AuthRepository;
import com.trading_simulator.backend.object.entity.Scenario;
import com.trading_simulator.backend.object.entity.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final AuthRepository authRepository;

    @Override
    public List<Scenario> getScenarios() {
        return List.of();
    }

    @Override
    public Scenario addScenario(Scenario scenario) {
        if (!authRepository.existsById(scenario.getOwner())) {
            throw new NotFoundException("User not found");
        }

        // Xử lý ký tự trong name, description

        scenario = scenario.toBuilder()
                .id(null)
                .build();
        scenario = scenarioRepository.save(scenario);
        return scenario;
    }

    @Override
    public Scenario updateScenario(UpdateScenario updateScenario) {
        Scenario scenario = scenarioRepository.findById(updateScenario.getId())
                .orElseThrow(() -> new NotFoundException("Scenario not found"));

        // xử lý ký tự

        scenario = scenario.toBuilder()
                .name(updateScenario.getName())
                .description(updateScenario.getDescription())
                .pin(updateScenario.getPin())
                .status(updateScenario.getStatus())
                .updatedAt(Instant.now())
                .build();
        scenario = scenarioRepository.save(scenario);
        return scenario;
    }

    @Override
    public void deleteByIds(List<String> ids) {

    }

    @Override
    public void deleteByOwners(List<String> owners) {
        
    }
}
