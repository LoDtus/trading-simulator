package com.trading_simulator.backend.object.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Schema(description = "Thông tin đăng ký tài khoản")
public class SignUpRequest {
    @Schema(description = "Email người dùng", example = "example@mail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    private String email;

    @Schema(description = "Tên đăng nhập người dùng", example = "username")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Mật khẩu", example = "Password123")
    @NotBlank(message = "Password is required")
    private String password;

    @Schema(description = "Quốc gia hiện tại", example = "Vietnam")
    private String nation;

    @Schema(description = "Thành phố hiện tại", example = "Hanoi")
    private String city;

    @Schema(description = "Ngày sinh người dùng", example = "2000-01-30")
    @Past
    private Instant dateOfBirth;

    @Schema(description = "Duy trì đăng nhập", example = "true")
    @NotNull(message =  "Remember me is required")
    private Boolean rememberMe;
}
