package com.trading_simulator.backend.service;

import com.trading_simulator.backend.common.util.CommonUtil;
import com.trading_simulator.backend.config.exception.BusinessException;
import com.trading_simulator.backend.config.exception.NotFoundException;
import com.trading_simulator.backend.object.entity.Auth;
import com.trading_simulator.backend.object.entity.AuthRepository;
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

import javax.annotation.Nullable;
import java.security.Key;
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
    private final AuthRepository authRepository;
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
    public String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
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
        if (userDetails instanceof Auth auth) {
            return (userId.equals(auth.getId()));
        }
        return false;
    }

    @Override
    public RefreshToken generateRefreshToken(Auth auth) {
        if (!authRepository.existsById(auth.getId())) {
            throw new NotFoundException("User not found");
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .token(CommonUtil.generateUniqueUUID(refreshTokenRepository))
                .owner(auth.getId())
                .exp(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.DAYS))
                .build();
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public String generateAccessToken(
            Auth auth,
            @Nullable RefreshToken refreshToken
    ) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", auth.getId());
        claims.put("sid", refreshToken != null ? refreshToken.getToken() : null);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).toMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void generateTokens(
            HttpServletResponse response,
            Auth auth,
            Boolean rememberMe
    ) {
        RefreshToken refreshToken = rememberMe ? generateRefreshToken(auth) : null;
        String accessToken = generateAccessToken(auth, refreshToken);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(rememberMe ? Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).getSeconds() : -1)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
    }

    @Override
    public Auth refreshAccessToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String accessToken = extractTokenFromCookie(request, "accessToken");
        String userId = extractValueFromToken(accessToken, "user");
        String sid = extractValueFromToken(accessToken, "sid");

        if (sid == null) { // rememberMe = false
            //sign out
        };

        // Kiểm tra tồn tại của người dùng và refresh token
        Auth auth = authRepository.findById(userId).orElse(null);
        RefreshToken refreshToken = refreshTokenRepository.findById(sid).orElse(null);
        if (auth == null || refreshToken == null) {
            // sign out

            throw new NotFoundException(auth == null ? "User not found" : "Refresh token not found");
        }

        // Kiểm tra refresh token
        if (refreshToken.getExp().isBefore(Instant.now())) {
            // sign out
            throw new BusinessException("Refresh token expired", "REFRESH_TOKEN_EXPIRED");
        }

        // Refresh Token
        String newAccessToken = generateAccessToken(auth, refreshToken);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(Duration.ofMinutes(ACCESS_TOKEN_EXPIRATION).getSeconds())
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        return auth;
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
