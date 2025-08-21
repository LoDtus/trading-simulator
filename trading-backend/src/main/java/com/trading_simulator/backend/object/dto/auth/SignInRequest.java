package com.trading_simulator.backend.object.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInRequest {
    @NotBlank(message = "Email or username is required")
    private String emailOrUsername;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Remember me is required")
    private Boolean rememberMe;
}
