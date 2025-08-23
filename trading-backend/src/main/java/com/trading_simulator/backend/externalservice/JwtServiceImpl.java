package com.trading_simulator.backend.externalservice;

import com.trading_simulator.backend.domain.auth.Auth;
import com.trading_simulator.backend.domain.auth.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

import java.security.Key;
import java.time.Instant;
import java.util.*;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${SECRET_KEY}")
    private String key;

    @Value("${ACCESS_TOKEN_EXPIRATION}")
    private Long accessTokenExpiration;

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private Long refreshTokenExpiration;

    @Value("${RESET_PASSWORD_TOKEN_EXPIRATION}")
    private Long resetPasswordTokenExpiration;

    private final AuthService authService;
    private final UserDetailsService userDetailsService;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
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
            // Trả về claim từ token hết hạn (jjwt vẫn cho phép truy cập claims khi token đã expired)
            return ex.getClaims().get(claimKey, nameClass);
        }
    }

    @Override
    public String extractId(String token) {
        return extractClaim(token, "id", String.class);
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, "email", String.class);
    }

    @Override
    public String extractRole(String token) {
        return extractClaim(token, "role", String.class);
    }

    @Override
    public String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            System.out.println("No cookies found in request");
            return null;
        }

        System.out.println("Cookies found: " + Arrays.toString(request.getCookies()));
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    public Boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, "exp", Date.class);
        return expiration.before(new Date());
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        final String id = extractId(token);
        final String role = extractRole(token);

        // Kiểm tra xem token đã hết hạn hay chưa
        if (isTokenExpired(token)) {
            System.out.println("Token het han");
            return false;
        }
        // Kiểm tra xem userDetails có phải là instance của User hay không. Nếu đúng thì tiếp tục so sánh xem email, id
        // và role có khớp với dữ liệu được lưu trong cơ sở dữ liệu hay không
        if (userDetails instanceof Auth auth) {
            return (email.equals(auth.getEmail()) &&
                    id.equals(auth.getId()) &&
                    role.equals(auth.getRole())
            );
        }
        return false;
    }

    // Token bao gồm: id, email, role, cùng với sub sử dụng email
    @Override
    public String generateAccessToken(Auth auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", auth.getId());
        claims.put("email", auth.getEmail());
        claims.put("role", auth.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getEmail()) // tạo subject, giúp server nhận ra user nhanh chóng mà không phải scan tìm lại
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // refreshToken chỉ được dùng với mục đích duy nhất là cấp lại accessToken mới
    // Nó không được gửi đi trong bất kỳ request nào khác. Request token cũng chỉ được sử dụng khi accessToken hết hạn
    @Override
    public String generateRefreshToken(Auth auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", auth.getId());
        claims.put("email", auth.getEmail());
        claims.put("role", auth.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateResetPasswordToken(Auth auth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", auth.getId());
        claims.put("email", auth.getEmail());
        claims.put("username", auth.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + resetPasswordTokenExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public UserDetails refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            String refreshToken
    ) {
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token missing");
        }
        String email = extractEmail(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new RuntimeException("User not found");
        }
        if (!isTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String newAccessToken = generateAccessToken((Auth) userDetails);
        setTokensToCookies(newAccessToken, null, true, response);
        return userDetails;
    }

    @Override
    public void setTokensToCookies(String accessToken, String refreshToken, Boolean rememberMe, HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(rememberMe ? (int) (getAccessTokenExpiration() / 1000) : -1)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        System.out.println("Set access cookie");

        if (refreshToken != null) {
            ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax")
                    .secure(false)
                    .maxAge(rememberMe ? (int) (getRefreshTokenExpiration() / 1000) : -1)
                    .build();
            response.addHeader("Set-Cookie", refreshTokenCookie.toString());
            System.out.println("Set refresh cookie");
        }

        ResponseCookie rememberMeCookie = ResponseCookie.from("rememberMe", rememberMe.toString())
                .httpOnly(false)
                .path("/")
                .sameSite("Lax")
                .secure(false)
                .maxAge(rememberMe ? (int) (getRefreshTokenExpiration() / 1000) : -1)
                .build();
        response.addHeader("Set-Cookie", rememberMeCookie.toString());
        System.out.println("Set remember me cookie");
    }

    @Override
    public void clearTokensFromCookies(HttpServletResponse response) {
        List<String> cookiesToClear = List.of("accessToken", "refreshToken");

        for (String cookieName : cookiesToClear) {
            Cookie cookie = new Cookie(cookieName, "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setHttpOnly("refreshToken".equals(cookieName));
            response.addCookie(cookie);
        }
    }
}
