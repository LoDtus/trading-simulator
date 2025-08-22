package com.trading_simulator.backend.object.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {
    private String id;
    private String role;
    private String nation;
    private String city;
    private String status;
    private String active;
    private List<Instant> dateOfBirth;
    private List<Instant> createdAt;

    private String keyword;
}