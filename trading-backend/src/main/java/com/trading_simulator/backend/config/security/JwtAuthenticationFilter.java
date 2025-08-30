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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
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

    private void setAuthenticationInContext(
            HttpServletRequest request,
            UserDetails userDetails
    ) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void handleMissingAccessToken(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String sid = jwtService.extractValueFromCookie(request, "sid");
        if (sid == null || sid.isBlank()) {
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Refresh token id is missing"
            );
            return;
        }

        RefreshToken refreshToken = refreshTokenRepository.findById(sid).orElse(null);
        if (refreshToken == null) {
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: Refresh token not found"
            );
            return;
        }

        try {
            String newAccessToken = jwtService.refreshAccessToken(request, response);
            User user = userRepository.findById(refreshToken.getOwner())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            if (!userDetails.isEnabled()) {
                CommonUtils.sendErrorResponse(
                        response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Error: User is disabled"
                );
                return;
            }
            setAuthenticationInContext(request, userDetails);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error: " + e.getMessage()
            );
            // sign out
        }
    }

    private void processTokenAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            String accessToken,
            FilterChain filterChain
    ) throws IOException, ServletException {
        try {
            String userId = jwtService.extractValueFromToken(accessToken, "user");
            if (userId == null) {
                CommonUtils.sendErrorResponse(
                        response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Error: Missing user in token"
                );
                return;
            }

            if (!jwtService.isTokenValid(accessToken, userId)) {
                CommonUtils.sendErrorResponse(
                        response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Error: Invalid access token"
                );
                return;
            }

            System.out.println(userId);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            System.out.println(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            if (!userDetails.isEnabled()) {
                CommonUtils.sendErrorResponse(
                        response,
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Error: User is disabled"
                );
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                setAuthenticationInContext(request, userDetails);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            CommonUtils.sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication failed: " + e.getMessage()
            );
        }
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.extractValueFromCookie(request, "accessToken");
        if (accessToken == null) {
            handleMissingAccessToken(request, response, filterChain);
            return;
        }
        processTokenAuthentication(request, response, accessToken, filterChain);
    }
}