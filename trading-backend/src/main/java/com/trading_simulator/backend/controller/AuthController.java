package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.common.enums.RoleDb;
import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import com.trading_simulator.backend.object.entity.Auth;
import com.trading_simulator.backend.object.entity.Profile;
import com.trading_simulator.backend.service.entityservice.AuthService;
import com.trading_simulator.backend.service.entityservice.ProfileService;
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
public class AuthController {
    private final AuthService authService;
    private final ProfileService profileService;

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

        Profile profile = Profile.builder()
                .id(auth.getId())
                .dateOfBirth(request.getDateOfBirth())
                .address(List.of(request.getNation(), request.getCity()))
                .createdAt(Instant.now())
                .build();
        profile = profileService.save(profile);

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

        Profile profile = profileService.findById(auth.getId());
        UserInfo userInfo = UserInfo.builder()
                .id(auth.getId())
                .email(auth.getEmail())
                .username(auth.getUsername())
                .role(auth.getRole())

                .image(profile.getImage())
                .status(null)
                .bio(profile.getBio())
                .address(profile.getAddress())
                .dateOfBirth(profile.getDateOfBirth())
                .createdAt(profile.getCreatedAt())

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