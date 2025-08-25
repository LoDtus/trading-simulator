package com.trading_simulator.backend.object.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetPasswordTokenRepository extends MongoRepository<ResetPasswordToken, String> {
}
