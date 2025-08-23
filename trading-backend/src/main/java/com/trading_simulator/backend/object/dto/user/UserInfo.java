package com.trading_simulator.backend.object.dto.user;

import com.trading_simulator.backend.domain.rank.Rank;
import com.trading_simulator.backend.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserInfo {
    private String id;
    private String email;
    private String username;
    private Role role;

    private String image;
    private Instant status;
    private String bio;
    private List<String> address;
    private Instant dateOfBirth;
    private Instant createdAt;

    private Rank rank;
}
