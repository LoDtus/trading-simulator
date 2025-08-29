package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import com.trading_simulator.backend.object.entity.TradeRepository;
import com.trading_simulator.backend.service.UserService;
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

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Các API xác thực tài khoản người dùng")
public class AuthController {
    private final UserService userService;
    private final TradeRepository tradeRepository;

    @Operation(summary = "Đăng ký")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        UserInfo userInfo = userService.signUp(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "Đăng nhập")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest request) {
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
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Boolean all
    ) {
        userService.signOut(request, response, all);
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
        userService.forgotPassword(emailOrUsername);
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
        Boolean isValid = userService.checkResetPasswordToken(token);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Đặt lại mật khẩu thông qua chức năng quên mật khẩu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        userService.resetPassword(request);

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
        return userService.existsByEmail(email);
    }

    @Operation(summary = "Kiểm tra tồn tại của username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),
            @ApiResponse(responseCode = "404", description = "Email đã tồn tại"),
            @ApiResponse(responseCode = "404", description = "Tên đăng nhập đã tồn tại")
    })
    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestParam String username) {
        return userService.existsByUsername(username);
    }
}