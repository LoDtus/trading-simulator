package com.trading_simulator.backend.externalservice;

import com.trading_simulator.backend.domain.auth.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    <T> T extractClaim(String token, String claimKey, Class<T> nameClass);
    String extractId(String token);
    String extractEmail(String token);
    String extractRole(String token);
    String extractTokenFromCookie(HttpServletRequest request, String cookieName);

    Boolean isTokenExpired(String token);
    Boolean isTokenValid(String token, UserDetails userDetails);

    String generateAccessToken(Auth auth);
    String generateRefreshToken(Auth auth);
    String generateResetPasswordToken(Auth auth);

    UserDetails refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String refreshToken);
    void setTokensToCookies(String accessToken, String refreshToken, Boolean rememberMe, HttpServletResponse response);
    void clearTokensFromCookies(HttpServletResponse response);
}