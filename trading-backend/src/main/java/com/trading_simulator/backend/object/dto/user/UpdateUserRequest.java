package com.trading_simulator.backend.object.dto.user;

import com.trading_simulator.backend.object.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @Email(message = "Email invalid")
    private String email;
    private String password;
    private Role role;
    private boolean active;

    private MultipartFile image;
    private String status;
    private String bio;
    private List<String> address;

    @Past(message = "Date of birth must be in the past")
    private Instant dateOfBirth;
}
