package com.trading_simulator.backend.domain.refeshtoken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
}
