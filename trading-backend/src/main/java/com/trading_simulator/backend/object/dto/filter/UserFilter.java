package com.trading_simulator.backend.object.dto.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserFilter {
    private String role;
    private String nation;
    private String city;
    private String status;
    private String active;
    private List<Integer> rank;
    private List<Instant> dateOfBirth;
    private List<Instant> createdAt;

    private String keyword;
}