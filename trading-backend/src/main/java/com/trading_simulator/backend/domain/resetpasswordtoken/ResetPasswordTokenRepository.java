package com.trading_simulator.backend.domain.resetpasswordtoken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResetPasswordTokenRepository extends MongoRepository<ResetPasswordToken, String> {
}
