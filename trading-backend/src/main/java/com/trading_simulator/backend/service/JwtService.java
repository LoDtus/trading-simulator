package com.trading_simulator.backend.service;

import com.trading_simulator.backend.object.entity.Auth;
import com.trading_simulator.backend.object.entity.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    <T> T extractClaim(String token, String claimKey, Class<T> nameClass);
    String extractValueFromToken(String token, String key);
    String extractTokenFromCookie(HttpServletRequest request, String cookieName);

    Boolean isTokenExpired(String token);
    Boolean isTokenValid(String token, UserDetails userDetails);

    RefreshToken generateRefreshToken(Auth auth);
    String generateAccessToken(Auth auth, RefreshToken refreshToken);

    void generateTokens(HttpServletResponse response, Auth auth, Boolean rememberMe);
    Auth refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
    void clearAllCookies(HttpServletRequest request, HttpServletResponse response);
}