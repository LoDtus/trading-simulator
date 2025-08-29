package com.trading_simulator.backend.object.dto.user;

import com.trading_simulator.backend.object.entity.Rank;
import com.trading_simulator.backend.object.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
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

    @Email(message = "Email invalid")
    private String email;
    private String username;
    private Role role;

    private String image;
    private String status;
    private String bio;
    private List<String> address;

    @Past
    private Instant dateOfBirth;

    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;

    private Rank rank;
}
