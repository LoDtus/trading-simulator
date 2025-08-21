package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Scenario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScenarioRepository extends MongoRepository<Scenario, String> {
}
