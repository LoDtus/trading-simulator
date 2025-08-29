package com.trading_simulator.backend.service;

import com.trading_simulator.backend.common.util.CommonUtils;
import com.trading_simulator.backend.config.exception.BusinessException;
import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.object.dto.user.UpdateUserRequest;
import com.trading_simulator.backend.object.entity.*;
import com.trading_simulator.backend.object.dto.auth.ResetPasswordRequest;
import com.trading_simulator.backend.object.dto.auth.SignInRequest;
import com.trading_simulator.backend.object.dto.auth.SignUpRequest;
import com.trading_simulator.backend.object.dto.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${RESET_PASSWORD_TOKEN_EXPIRATION}")
    private Integer RESET_PASSWORD_TOKEN_EXPIRATION;

    @Value("${VUE_URL}")
    private String VUE_URL;

    @Override
    public UserInfo signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists", "EMAIL_EXISTS");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists", "USERNAME_EXISTS");
        }

        User user = User.builder()
                .id(null)
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
//                .role()
                .active(true)
                .build();
        user = userRepository.save(user);

        Profile profile = Profile.builder()
                .id(user.getId())
                .dateOfBirth(request.getDateOfBirth())
                .address(List.of(request.getNation(), request.getCity()))
                .createdAt(Instant.now())
                .build();
        profile = profileRepository.save(profile);

        return UserInfo.builder()

                .build();
    }

    @Override
    public UserInfo signIn(SignInRequest request) {
        User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername())
                .orElseThrow(() -> new BusinessException("Invalid credentials", "INVALID_CREDENTIALS"));

        // Check password
        if (!Objects.equals(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid credentials", "INVALID_CREDENTIALS");
        }

        // Lấy User
        Profile profile = profileRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())

                .image(profile.getImage())
                .status(null)
                .bio(profile.getBio())
                .address(profile.getAddress())
                .dateOfBirth(profile.getDateOfBirth())
                .createdAt(profile.getCreatedAt())

                .rank(null)
                .build();
    }

    @Override
    public void signOut(
            HttpServletRequest request,
            HttpServletResponse response,
            Boolean all
    ) {
        String accessToken = jwtService.extractValueFromCookie(request, "accessToken");
        String userId = jwtService.extractValueFromToken(accessToken, "user");
        String sid = jwtService.extractValueFromToken(accessToken, "sid");

        jwtService.clearAllCookies(request, response);
        if (all) {
            refreshTokenRepository.deleteByOwner(userId);
        } else {
            refreshTokenRepository.deleteById(sid);
        }

        // ngắt kết nối websocket
    }

    @Override
    public void forgotPassword(String emailOrUsername) {
        User user = userRepository.findByEmailOrUsername(emailOrUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));

        String token = CommonUtils.generateUniqueUUID(resetPasswordTokenRepository);
        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .token(token)
                .owner(user.getId())
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
                .orElseThrow(() -> new NotFoundException("Reset password token not found"));
        if (resetPasswordToken == null) {

        }

        // Kiểm tra còn tồn tại người dùng tương ứng hay không
        String userId = resetPasswordToken.getOwner();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (user == null) {
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
                .orElseThrow(() -> new NotFoundException("Reset password token not found"));
        if (resetPasswordToken == null) {

        }

        // Kiểm tra còn tồn tại người dùng tương ứng hay không
        String userId = resetPasswordToken.getOwner();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (user == null) {
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getToken());

        }

        // Kiểm tra token đã hết hạn hay chưa
        if (Instant.now().isAfter(resetPasswordToken.getExp())) {
            resetPasswordTokenRepository.deleteById(resetPasswordToken.getToken());

        }

        // Kiểm tra ký tự trong password trước khi lưu

        user = user.toBuilder()
                .password(newPassword)
                .build();
        user = userRepository.save(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
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

    @Override
    public void getUsers() {

    }

    @Override
    public UserInfo updateUser(
            HttpServletRequest request,
            UpdateUserRequest updateUserRequest
    ) {
        // Kiểm tra tồn tại
        User user = userRepository.findById(updateUserRequest.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Profile profile = profileRepository.findById(updateUserRequest.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Kiểm tra tồn tại email, username, ký tự trong email, username

        // Chỉ cho phép chính người dùng hiện tại hoặc admin có quyền cập nhật thông tin
        String accessToken = jwtService.extractValueFromCookie(request, "accessToken");
        String userId = jwtService.extractValueFromToken(accessToken, "user");
        if (!Objects.equals(updateUserRequest.getId(), userId)
            && !Objects.equals(updateUserRequest.getRole().getId(), "ROLE_ADMIN")
        ) {
            throw new BusinessException("User does not have permission", "ACCESS_DENIED");
        }

        // Lưu ảnh vào storage nếu có
        if (updateUserRequest.getImage() != null) {

        }

        // Sanitize các thẻ html nếu có
        String cleanBio = null;
        if (!updateUserRequest.getBio().isBlank()) {
            cleanBio = Jsoup.clean(updateUserRequest.getBio(), Safelist.basic());
        }

        // Lưu vào MongoDB
        user = user.toBuilder()
                .email("")
                .username("")
                .password(null)
                .role(null)
                .active(null)
                .build();
        user = userRepository.save(user);

        profile = profile.toBuilder()
                .image(null)
                .status(null)
                .bio(cleanBio != null ? cleanBio : profile.getBio())
                .dateOfBirth(null)
                .address(null)
                .updatedAt(Instant.now())
                .build();
        profile = profileRepository.save(profile);

        return UserInfo.builder()
                .bio(profile.getBio())
                .build();
    }

    @Override
    public void deleteUsers(List<String> ids) {

    }
}
