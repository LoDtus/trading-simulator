package com.trading_simulator.backend.domain.resetpasswordtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Override
    public ResetPasswordToken save(ResetPasswordToken token) {
        return resetPasswordTokenRepository.save(token);
    }

    @Override
    public Boolean deleteById(String id) {
        return null;
    }
}
