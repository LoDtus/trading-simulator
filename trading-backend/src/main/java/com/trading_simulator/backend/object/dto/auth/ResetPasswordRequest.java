package com.trading_simulator.backend.object.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
