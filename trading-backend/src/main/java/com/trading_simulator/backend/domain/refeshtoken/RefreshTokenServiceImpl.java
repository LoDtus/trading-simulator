package com.trading_simulator.backend.domain.refeshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken save(RefreshToken token) {
        return refreshTokenRepository.save(token);
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }
}
