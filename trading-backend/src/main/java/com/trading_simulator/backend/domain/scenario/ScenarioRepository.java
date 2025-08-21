package com.trading_simulator.backend.domain.scenario;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScenarioRepository extends MongoRepository<Scenario, String> {
}
