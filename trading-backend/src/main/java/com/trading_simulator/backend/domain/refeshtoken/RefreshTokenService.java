package com.trading_simulator.backend.domain.refeshtoken;

public interface RefreshTokenService {
    RefreshToken save(RefreshToken token);
    Boolean deleteById(String id);
}
