package com.trading_simulator.backend.object.dto.user;

import com.trading_simulator.backend.domain.role.Role;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
// xác thực trực tiếp tại đây
public class UpdateUserRequest {
    private String id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean active;

    private MultipartFile image;
    private String status;
    private String bio;
    private List<String> address;

    @PastOrPresent
    private Instant dateOfBirth;
}
