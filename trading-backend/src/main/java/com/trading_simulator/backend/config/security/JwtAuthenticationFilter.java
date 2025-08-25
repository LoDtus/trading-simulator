package com.trading_simulator.backend.config.security;

import com.trading_simulator.backend.object.entity.Auth;
import com.trading_simulator.backend.object.entity.AuthRepository;
import com.trading_simulator.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthRepository authRepository;
    private final List<String> publicEndpoints = Arrays.asList(
            "/ws",
            "/api/auth/sign-in",
            "/api/auth/sign-up",
            "/api/auth/forgot-password",
            "/api/auth/check-reset-password-token",
            "/api/auth/reset-password",
            "/api/auth/check-email-exists"
    );

    // Set authentication với token
    private void setAuthenticationInContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    // Hàm kiểm tra xem có hay thiếu accessToken
    private void handleMissingAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        System.out.println("JwtFilter - Access token expired");
        String refreshToken = jwtService.extractTokenFromCookie(request, "refreshToken");
        if (refreshToken == null) {
            System.out.println("JwtFilter - Access token missing");
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Both access and refresh tokens are missing"
//            );
            return;
        }

        try {
            // Xử lý tạo access token mới từ refresh token
            System.out.println("JwtFilter - Refresh access token");
            UserDetails userDetails = jwtService.refreshAccessToken(request, response);
            setAuthenticationInContext(request, userDetails);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("JwtFilter - Refresh token invalid: " + e.getMessage());
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Refresh token invalid: " + e.getMessage()
//            );
        }
    }

    private void handleExpiredAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            UserDetails userDetails
    ) throws IOException {
        String refreshToken = jwtService.extractTokenFromCookie(request, "refreshToken");
        if (refreshToken == null) {
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Access token expired and no refresh token available"
//            );
            return;
        }

        // Nếu refreshToken hợp lệ → xác thực refreshToken này
        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Access token expired and refresh token invalid"
//            );
            return;
        }

        // Tạo accessToken mới
        String newAccessToken = jwtService.generateAccessToken((Auth) userDetails, null);
        jwtService.generateTokens(response, (Auth) userDetails, true);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            setAuthenticationInContext(request, userDetails);
        }
    }

    private void processTokenAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            String accessToken
    ) throws IOException {
        String userId = jwtService.extractValueFromToken(accessToken, "user");
        if (userId == null) {
            System.out.println("Invalid token: Missing user");
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Invalid token: missing email"
//            );
            return;
        }

        Auth auth = authRepository.findById(userId).orElse(null);
        if (auth == null) {
            System.out.println("Invalid token: User not found by id");
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Invalid token: missing email"
//            );
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(auth.getEmail());
        if (userDetails == null) {
            System.out.println("Invalid token: User not found by email");
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "User not found"
//            );
            return;
        }

        // Kiểm tra thời hạn của access token
        if (jwtService.isTokenExpired(accessToken)) {
            handleExpiredAccessToken(request, response, userDetails);
            return;
        }

        // Nếu accessToken chưa hết hạn → xác thực accessToken này
        if (!jwtService.isTokenValid(accessToken, userDetails)) {
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Invalid token"
//            );
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            setAuthenticationInContext(request, userDetails);
        }
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Bỏ qua các public endpoint, không phải xác thực token
        boolean isPublic = publicEndpoints.stream().anyMatch(request.getRequestURI()::startsWith);
        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.extractTokenFromCookie(request, "accessToken");
        if (accessToken == null) { // Nếu thiếu accessToken
            handleMissingAccessToken(request, response, filterChain);
            return;
        }

        try { // Nếu đầy đủ accessToken → thực hiện xác thực với accessToken đó
            processTokenAuthentication(request, response, accessToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
//            NotificationUtils.sendErrorResponse(
//                    response,
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    "Authentication failed: " + e.getMessage()
//            );
        }
    }
}