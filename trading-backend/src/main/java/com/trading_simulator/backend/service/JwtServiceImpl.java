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
// giải quyết lại mà không cần phải kiểm tra tồn tại của auth, tránh việc mỗi 15p lại tìm auth rất mất tgian mà trong khi trong chính rt đã lưu owner rồi
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

        for (Cookie cookie : request.getCookies()) {
            if (Objects.equals(cookie.getName(), cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public Boolean isTokenExpired(String token) { // fix
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException ex) { // Token đã hết hạn
            return true;
        } catch (JwtException ex) { // Token không hợp lệ
            return true;
        }
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String userId = extractValueFromToken(token, "user");
        if (isTokenExpired(token)) {
            return false;
        }
        if (userDetails instanceof User user) {
            return (Objects.equals(userId, user.getId()));
        }
        return false;
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
    public RefreshToken generateRefreshToken(
            HttpServletRequest request,
            String userId
    ) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found");
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .token(CommonUtils.generateUniqueUUID(refreshTokenRepository))
                .owner(userId)
                .deviceFingerprint(generateDeviceFingerprint(request))
//                .exp(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.DAYS))
                .exp(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public String generateAccessToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).toMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void generateTokens(
            HttpServletRequest request,
            HttpServletResponse response,
            User user,
            Boolean rememberMe
    ) {
        String accessToken = generateAccessToken(user.getId());
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(rememberMe ? Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).getSeconds() : -1)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        RefreshToken refreshToken = rememberMe ? generateRefreshToken(request, user.getId()) : null;
        if (rememberMe && refreshToken != null) {
            ResponseCookie sidCookie = ResponseCookie.from("sid", refreshToken.getToken())
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax")
                    .secure(false)
//                    .maxAge(Duration.ofDays(REFRESH_TOKEN_EXPIRATION).getSeconds())
                    .maxAge(Duration.ofMinutes(REFRESH_TOKEN_EXPIRATION).getSeconds())
                    .build();
            response.addHeader("Set-Cookie", sidCookie.toString());
        }
    }

    @Override
    public String refreshAccessToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String sid = extractValueFromCookie(request, "sid");
        if (sid == null) { // ~ rememberMe = false
            //sign out
            throw new NotFoundException("Sid not found");
        };

        RefreshToken refreshToken = refreshTokenRepository.findById(sid).orElse(null);
        if (refreshToken == null) {
            // sign out

            throw new NotFoundException("Refresh token not found");
        }

        String currentFingerprint = generateDeviceFingerprint(request);
        if (
                refreshToken.getExp().isBefore(Instant.now())
                || !Objects.equals(refreshToken.getDeviceFingerprint(), currentFingerprint)
        ) {
            // sign out
            throw new BusinessException("Refresh token expired", "REFRESH_TOKEN_EXPIRED");
        }

        // Refresh Token
        String newAccessToken = generateAccessToken(refreshToken.getOwner());
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
                Cookie cookieToDelete = new Cookie(cookie.getName(), "");
                cookieToDelete.setPath("/");
                cookieToDelete.setMaxAge(0);
                cookieToDelete.setHttpOnly(cookie.isHttpOnly());
                cookieToDelete.setSecure(cookie.getSecure());
                response.addCookie(cookieToDelete);
            }
        }
    }
}
