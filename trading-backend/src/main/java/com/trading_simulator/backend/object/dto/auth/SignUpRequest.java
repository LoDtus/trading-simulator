package com.trading_simulator.backend.object.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email not valid")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private String nation;
    private String city;
    private Instant dateOfBirth;

    @NotNull(message =  "Remember me is required")
    private Boolean rememberMe;
}
