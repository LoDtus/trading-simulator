package com.trading_simulator.backend.service;

import com.trading_simulator.backend.common.util.CommonUtils;
import com.trading_simulator.backend.config.exception.BusinessException;
import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.object.entity.User;
import com.trading_simulator.backend.object.entity.UserRepository;
import com.trading_simulator.backend.object.entity.RefreshToken;
import com.trading_simulator.backend.object.entity.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${ACCESS_TOKEN_EXPIRATION}")
    private Integer ACCESS_TOKEN_EXPIRATION;

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private Integer REFRESH_TOKEN_EXPIRATION;

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public <T> T extractClaim(String token, String claimKey, Class<T> nameClass) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get(claimKey, nameClass);
        } catch (ExpiredJwtException ex) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
                servletRequestAttributes.getRequest().setAttribute("expired", true);
            }
            return ex.getClaims().get(claimKey, nameClass);
        } catch (JwtException ex) {
            throw new BusinessException("Invalid token: " + ex.getMessage(), "INVALID_TOKEN");
        }
    }

    @Override
    public String extractValueFromToken(String token, String key) {
        return extractClaim(token, key, String.class);
    }

    @Override
    public String extractValueFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (JwtException ex) {
            return true;
        }
    }

    @Override
    public Boolean isTokenValid(String token, String userId) {
        if (isTokenExpired(token)) {
            return false;
        }
        String tokenUserId = extractValueFromToken(token, "user");
        return Objects.equals(tokenUserId, userId);
    }

    @Override
    public String generateDeviceFingerprint(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        String input = userAgent + ip;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    @Override
    public RefreshToken generateRefreshToken(HttpServletRequest request, String userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(CommonUtils.generateUniqueUUID(refreshTokenRepository))
                .owner(userId)
                .deviceFingerprint(generateDeviceFingerprint(request))
                .exp(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public String generateAccessToken(String userId, boolean rememberMe) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userId);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256);

        if (rememberMe) {
            builder.setExpiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).toMillis()));
        } else {
            // Đặt thời hạn dài hơn cho session cookie, ví dụ: 24 giờ
            builder.setExpiration(new Date(System.currentTimeMillis() + Duration.ofHours(24).toMillis()));
        }

        return builder.compact();
    }

    @Override
    public void generateTokens(HttpServletRequest request, HttpServletResponse response, User user, Boolean rememberMe) {
        String accessToken = generateAccessToken(user.getId(), rememberMe);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(rememberMe ? Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).getSeconds() : -1)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        if (rememberMe) {
            RefreshToken refreshToken = generateRefreshToken(request, user.getId());
            ResponseCookie sidCookie = ResponseCookie.from("sid", refreshToken.getToken())
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax")
                    .secure(false)
                    .maxAge(Duration.ofMinutes(REFRESH_TOKEN_EXPIRATION).getSeconds())
                    .build();
            response.addHeader("Set-Cookie", sidCookie.toString());
        }
    }

    @Override
    public String refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String sid = extractValueFromCookie(request, "sid");
        if (sid == null) {
            // sign out
            throw new NotFoundException("Sid not found");
        }

        RefreshToken refreshToken = refreshTokenRepository.findById(sid)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));

        // Xử lý nếu refresh token không hợp lệ
        String currentFingerprint = generateDeviceFingerprint(request);
        if (refreshToken.getExp().isBefore(Instant.now()) || !Objects.equals(refreshToken.getDeviceFingerprint(), currentFingerprint)) {
            refreshTokenRepository.delete(refreshToken);
            // sign out
            throw new BusinessException("Refresh token expired or invalid device", "REFRESH_TOKEN_EXPIRED");
        }

        String newAccessToken = generateAccessToken(refreshToken.getOwner(), true);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).getSeconds())
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        return newAccessToken;
    }

    @Override
    public void clearAllCookies(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                ResponseCookie cookieToDelete = ResponseCookie.from(cookie.getName(), "")
                        .path("/")
                        .maxAge(0)
                        .httpOnly(true)
                        .secure(false)
                        .build();
                response.addHeader("Set-Cookie", cookieToDelete.toString());
            }
        }
    }
}