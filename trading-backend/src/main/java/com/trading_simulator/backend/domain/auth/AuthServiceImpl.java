package com.trading_simulator.backend.domain.auth;

import com.trading_simulator.backend.common.util.CommonUtil;
import com.trading_simulator.backend.config.exception.BusinessException;
import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.domain.resetpasswordtoken.ResetPasswordToken;
import com.trading_simulator.backend.domain.resetpasswordtoken.ResetPasswordTokenRepository;
import com.trading_simulator.backend.domain.user.User;
import com.trading_simulator.backend.domain.user.UserRepository;
import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Value("${RESET_PASSWORD_TOKEN_EXPIRATION}")
    private Integer RESET_PASSWORD_TOKEN_EXPIRATION;

    @Value("${VUE_URL}")
    private String VUE_URL;

    @Override
    public UserInfo signUp(SignUpRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists", "EMAIL_EXISTS");
        }
        if (authRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists", "USERNAME_EXISTS");
        }

        Auth auth = Auth.builder()
                .id(null)
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
//                .role()
                .active(true)
                .build();
        auth = authRepository.save(auth);

        User user = User.builder()
                .id(auth.getId())
                .dateOfBirth(request.getDateOfBirth())
                .address(List.of(request.getNation(), request.getCity()))
                .createdAt(Instant.now())
                .build();
        user = userRepository.save(user);

        return UserInfo.builder()

                .build();
    }

    @Override
    public UserInfo signIn(SignInRequest request) {
        Auth auth = authRepository.findByEmailOrUsername(request.getEmailOrUsername())
                .orElseThrow(() -> new BusinessException("Invalid credentials", "INVALID_CREDENTIALS"));

        // Check password
        if (request.getPassword().equals(auth.getPassword())) {
            throw new BusinessException("Invalid credentials", "INVALID_CREDENTIALS");
        }

        // Lấy User
        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return UserInfo.builder()
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
    }

    @Override
    public void signOut(HttpServletRequest request) {

    }

    @Override
    public void forgotPassword(String emailOrUsername) {
        Auth auth = authRepository.findByEmailOrUsername(emailOrUsername)
                .orElseThrow(() -> new NotFoundException("User not found: " + emailOrUsername));

        String token = CommonUtil.generateUniqueUUID(resetPasswordTokenRepository);
        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .token(token)
                .owner(auth.getId())
                .exp(Instant.now().plus(RESET_PASSWORD_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .build();
        resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);

        String resetPasswordUrl = VUE_URL + "/reset-password?token=" + token;
        // Gửi mail
    }

    @Override
    public Boolean checkResetPasswordToken(String token) {
        // Kiểm tra còn tồn tại token hay không
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(token)
                .orElseThrow(() -> new NotFoundException("Reset password token not found: " + token));
        if (resetPasswordToken == null) {

        }

        // Kiểm tra còn tồn tại người dùng tương ứng hay không
        String userId = resetPasswordToken.getOwner();
        Auth auth = authRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
        if (auth == null) {
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getToken());

        }

        // Kiểm tra token đã hết hạn hay chưa
        if (Instant.now().isAfter(resetPasswordToken.getExp())) {
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getToken());
        }
        return true;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();

        // Kiểm tra còn tồn tại token hay không
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(token)
                .orElseThrow(() -> new NotFoundException("Reset password token not found: " + token));
        if (resetPasswordToken == null) {

        }

        // Kiểm tra còn tồn tại người dùng tương ứng hay không
        String userId = resetPasswordToken.getOwner();
        Auth auth = authRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));
        if (auth == null) {
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getToken());

        }

        // Kiểm tra token đã hết hạn hay chưa
        if (Instant.now().isAfter(resetPasswordToken.getExp())) {
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getToken());

        }

        // Kiểm tra ký tự trong password trước khi lưu

        auth = auth.toBuilder()
                .password(newPassword)
                .build();
        auth = authRepository.save(auth);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return authRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return authRepository.existsByUsername(username);
    }

    @Override
    public void refreshToken(
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
    }
}
