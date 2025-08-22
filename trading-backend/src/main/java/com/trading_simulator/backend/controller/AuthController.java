package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.common.enums.RoleDb;
import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import com.trading_simulator.backend.domain.auth.Auth;
import com.trading_simulator.backend.domain.user.User;
import com.trading_simulator.backend.domain.auth.AuthService;
import com.trading_simulator.backend.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Các API xác thực tài khoản người dùng")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Operation(
            summary = "Đăng ký tài khoản",
            description = "Đăng ký và tạo mới tài khoản cho người dùng"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        if (authService.existsByEmailOrUsername(request.getEmail())
            || authService.existsByEmailOrUsername(request.getUsername())
        ) {
            return ResponseEntity.ok("");
        }

        Auth auth = Auth.builder()
                .id(null)
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(RoleDb.ROLE_USER)
                .active(true)
                .build();
        auth = authService.save(auth);

        User user = User.builder()
                .id(auth.getId())
                .dateOfBirth(request.getDateOfBirth())
                .address(List.of(request.getNation(), request.getCity()))
                .createdAt(Instant.now())
                .build();
        user = userService.save(user);

        return ResponseEntity.ok("");
    }

    @GetMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {
        Auth auth = authService.findByEmail(request.getEmailOrUsername());
        if (auth == null) {
            auth = authService.findByUsername(request.getEmailOrUsername());
        }
        if (auth == null) {
            return ResponseEntity.ok("");
        }

        if (!Objects.equals(auth.getPassword(), request.getPassword())) {
            return ResponseEntity.ok("");
        }

        User user = userService.findById(auth.getId());
        UserInfo userInfo = UserInfo.builder()
                .id(auth.getId())
                .email(auth.getEmail())
                .username(auth.getUsername())
                .role(auth.getRole())

                .image(user.getImage())
                .status(null)
                .bio(user.getBio())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .createdAt(user.getCreatedAt())

                .rank(null)
                .build();

        return ResponseEntity.ok("");
    }

    @GetMapping("/sign-out")
    public ResponseEntity<?> signOut() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String emailOrUsername) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/check-reset-password-token")
    public ResponseEntity<?> checkResetPasswordToken(@RequestParam String token) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {


        return ResponseEntity.ok("");
    }
}