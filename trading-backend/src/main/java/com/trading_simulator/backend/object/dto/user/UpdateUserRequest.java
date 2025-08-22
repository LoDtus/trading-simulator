package com.trading_simulator.backend.object.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    private String id;
    private String username;
    private String email;
    private String role;

    private String image;
    private Instant status;
    private String bio;
    private List<String> address;
    private Instant dateOfBirth;
    private Instant createdAt;
}
