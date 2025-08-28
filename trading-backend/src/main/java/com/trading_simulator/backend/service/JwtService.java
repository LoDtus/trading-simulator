package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.User;
import com.trading_simulator.backend.object.entity.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    <T> T extractClaim(String token, String claimKey, Class<T> nameClass);
    String extractValueFromCookie(HttpServletRequest request, String cookieName);
    String extractValueFromToken(String token, String key);

    Boolean isTokenExpired(String token);
    Boolean isTokenValid(String token, UserDetails userDetails);

    String generateDeviceFingerprint(HttpServletRequest request);
    RefreshToken generateRefreshToken(HttpServletRequest request, String userId);
    String generateAccessToken(String userId);

    void generateTokens(HttpServletRequest request, HttpServletResponse response, User user, Boolean rememberMe);
    String refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
    void clearAllCookies(HttpServletRequest request, HttpServletResponse response);
}