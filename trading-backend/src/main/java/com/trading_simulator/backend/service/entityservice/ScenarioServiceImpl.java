package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioServiceImpl implements ScenarioService {
    private final ScenarioRepository scenarioRepository;

    @Override
    public List<Scenario> find() {
        return List.of();
    }

    @Override
    public Scenario save(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    @Override
    public Boolean deleteById(String id) {
        if (!scenarioRepository.existsById(id)) return false;
        scenarioRepository.deleteById(id);
        return true;
    }
}
