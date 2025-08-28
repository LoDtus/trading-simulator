package com.trading_simulator.backend.config.security;

import com.trading_simulator.backend.common.util.CommonUtils;
import com.trading_simulator.backend.object.entity.*;
import com.trading_simulator.backend.service.JwtService;
import jakarta.annotation.PostConstruct;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository authRepository;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ApiPermissionRepository apiPermissionRepository;

    private List<ApiPermission> permissionsCache;

    @PostConstruct
    public void loadPermissions() {
        this.permissionsCache = apiPermissionRepository.findByEnabledTrue();
    }

    private Boolean isPublicEndpoint(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return permissionsCache.stream().anyMatch(p -> {
            String regexPattern = p.getPattern().contains("**")
                    ? p.getPattern().replace("**", ".*")
                    : p.getPattern();
            boolean patternMatches = uri.matches(regexPattern);
            boolean methodMatches = p.getMethod() == null
                    || p.getMethod().equalsIgnoreCase("ALL")
                    || p.getMethod().equals("*")
                    || p.getMethod().trim().equalsIgnoreCase(method);
            boolean isPublic = p.getRoleIds() == null || p.getRoleIds().isEmpty();
            return patternMatches && methodMatches && isPublic;
        });
    }

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

    // Xử lý khi request gửi lên thiếu accessToken (do hết hạn hoặc nguyên nhân khác)
    private void handleMissingAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        System.out.println("JwtFilter: Access token is missing");
        String sid = jwtService.extractValueFromCookie(request, "sid");
        if (sid == null || sid.isBlank()) {
            System.out.println("JwtFilter: Refresh token id is missing");
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Refresh token id is missing"
            );
            // sign out
            return;
        }

        if (!refreshTokenRepository.existsById(sid)) {
            System.out.println("JwtFilter: Refresh token not found");
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Refresh token not found"
            );
            // sign out
            return;
        }

        try {
            System.out.println("JwtFilter: Refresh access token exists");
            String newAccessToken = jwtService.refreshAccessToken(request, response);
//            UserDetails userDetails = jwtService.refreshAccessToken(request, response);
//            setAuthenticationInContext(request, userDetails);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("JwtFilter - Refresh token invalid: " + e.getMessage());
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error - Refresh token invalid: " + e.getMessage()
            );
            // sign out
        }
    }

    // Xác thực với accessToken
    private void processTokenAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            String accessToken
    ) throws IOException {
        System.out.println("JwtFilter: Access token exists");
        String userId = jwtService.extractValueFromToken(accessToken, "user");
        if (userId == null) {
            System.out.println("JwtFilter: Missing user");
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Missing user in token"
            );
            // sign out
            return;
        }

        User user = authRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("JwtFilter: User not found by token");
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: User not found by token"
            );
            // sign out
            return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
        if (userDetails == null) {
            System.out.println("JwtFilter: User not found by email");
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: User not found by email"
            );
            // sign out
            return;
        }

        // Xác thực accessToken
        if (!jwtService.isTokenValid(accessToken, userDetails)) { // Chỗ này đang bị lòng vòng, hàm isTokenValid đang làm lại các bước bên trên
            System.out.println("JwtFilter: Invalid access token");
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Invalid access token"
            );
            // sign out
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
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.extractValueFromCookie(request, "accessToken");
        if (accessToken == null) { // Nếu thiếu accessToken, hoặc accessToken bị xóa khi hết hạn
            handleMissingAccessToken(request, response, filterChain);
            return;
        }

        try { // Nếu đầy đủ accessToken → thực hiện xác thực với accessToken đó
            processTokenAuthentication(request, response, accessToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication failed: " + e.getMessage()
            );
            // sign out
        }
    }
}