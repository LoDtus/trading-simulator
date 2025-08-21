package com.trading_simulator.backend.domain.trade;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TradeRepository extends MongoRepository<Trade, String> {
}
