package com.trading_simulator.backend.service.entityservice;

import com.trading_simulator.backend.object.entity.Rank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RankRepository extends MongoRepository<Rank, String> {
}
