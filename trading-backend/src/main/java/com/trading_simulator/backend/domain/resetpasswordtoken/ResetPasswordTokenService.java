package com.trading_simulator.backend.domain.resetpasswordtoken;

public interface ResetPasswordTokenService {
    ResetPasswordToken findByToken(String token);
    ResetPasswordToken save(ResetPasswordToken token);
    Boolean deleteById(String id);
}
