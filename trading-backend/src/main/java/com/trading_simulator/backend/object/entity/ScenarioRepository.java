package com.trading_simulator.backend.object.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScenarioRepository extends MongoRepository<Scenario, String> {
}
