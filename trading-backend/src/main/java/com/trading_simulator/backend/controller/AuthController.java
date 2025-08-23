package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.domain.refeshtoken.RefreshTokenService;
import com.trading_simulator.backend.domain.resetpasswordtoken.ResetPasswordToken;
import com.trading_simulator.backend.domain.resetpasswordtoken.ResetPasswordTokenService;
import com.trading_simulator.backend.externalservice.JwtService;
import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import com.trading_simulator.backend.domain.auth.Auth;
import com.trading_simulator.backend.domain.user.User;
import com.trading_simulator.backend.domain.auth.AuthService;
import com.trading_simulator.backend.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Các API xác thực tài khoản người dùng")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final JwtService jwtService;

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
        if (authService.existsByEmail(request.getEmail())) {
            return ResponseEntity.ok("");
        }
        if (authService.existsByUsername(request.getUsername())) {
            return ResponseEntity.ok("");
        }

        Auth auth = Auth.builder()
                .id(null)
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
//                .role()
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
        Auth auth = authService.findByEmailOrUsername(request.getEmailOrUsername());
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
    public ResponseEntity<?> signOut(
            HttpServletRequest request
    ) {


        return ResponseEntity.ok("");
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String emailOrUsername) {
        Auth auth = authService.findByEmailOrUsername(emailOrUsername);

        String token = jwtService.generateResetPasswordToken(auth);

        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .token(token)
                .owner(auth.getId())
                .exp(null)
                .build();
        resetPasswordToken = resetPasswordTokenService.save(resetPasswordToken);

        // Gửi mail

        return ResponseEntity.ok("");
    }

    @GetMapping("/check-reset-password-token")
    public ResponseEntity<?> checkResetPasswordToken(
            @RequestParam String token
    ) {
        // Giải mã token
        String id = token;

        if (resetPasswordTokenService.findByToken(token) == null) {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.ok("");
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        String token = request.getToken();
        String password = request.getNewPassword();

        // giải mã token
        if (resetPasswordTokenService.findByToken(token) == null) {
            return ResponseEntity.ok("");
        }
        // Trích xuất id
        String id = token;
        Auth auth = authService.findById(id);
        if (auth == null) {
            return ResponseEntity.ok("");
        }

        // Kiểm tra ký tự trong password
        auth = auth.toBuilder()
                .password(password)
                .build();
        auth = authService.save(auth);
        return ResponseEntity.ok("");
    }

    @GetMapping("/check-email")
    public Boolean checkEmail(@RequestParam String email) {
        return authService.existsByEmail(email);
    }

    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestParam String username) {
        return authService.existsByUsername(username);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
//        String sessionId = extractSessionIdFromCookie(request);
//        if (sessionId == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing sessionId");
//        }
//
//        // 2. Kiểm tra sessionId trong DB/Redis
//        Session session = sessionService.findById(sessionId);
//        if (session == null || session.isExpired()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired session");
//        }
//
//        // 3. Sinh access token mới
//        String newAccessToken = tokenService.generateAccessToken(session.getUserId());

        // 4. Set access token vào HttpOnly cookie (tuỳ bạn, có thể trả qua JSON cũng được)
//        Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
//        accessTokenCookie.setHttpOnly(true);
//        accessTokenCookie.setSecure(true);
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge(15 * 60);
//        response.addCookie(accessTokenCookie);

        // 5. Trả response
        return ResponseEntity.ok(Map.of("message", "Access token refreshed"));
    }
}