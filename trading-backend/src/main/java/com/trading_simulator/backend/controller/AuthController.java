package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import com.trading_simulator.backend.domain.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Các API xác thực tài khoản người dùng")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Đăng ký")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        UserInfo userInfo = authService.signUp(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "Đăng nhập")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {
        UserInfo userInfo = authService.signIn(request);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Đăng xuất")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/sign-out")
    public ResponseEntity<?> signOut(
            HttpServletRequest request
    ) {
        authService.signOut(request);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Gửi yêu cầu quên và đặt lại mật khẩu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestParam String emailOrUsername
    ) {
        authService.forgotPassword(emailOrUsername);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Kiểm tra hợp lệ của token đặt lại mật khẩu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/check-reset-password-token")
    public ResponseEntity<?> checkResetPasswordToken(
            @RequestParam String token
    ) {
        Boolean isValid = authService.checkResetPasswordToken(token);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Đặt lại mật khẩu thông qua chức năng quên mật khẩu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Kiểm tra tồn tại của email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/check-email")
    public Boolean checkEmail(@RequestParam String email) {
        return authService.existsByEmail(email);
    }

    @Operation(summary = "Kiểm tra tồn tại của username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestParam String username) {
        return authService.existsByUsername(username);
    }

    @Operation(summary = "Cấp lại Access Token thông qua Refresh Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.refreshToken(request, response);
        return ResponseEntity.ok(Map.of("message", "Access token refreshed"));
    }
}