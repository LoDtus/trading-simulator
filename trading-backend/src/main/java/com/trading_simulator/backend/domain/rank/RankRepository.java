package com.trading_simulator.backend.domain.rank;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RankRepository extends MongoRepository<Rank, String> {
}
