package com.trading_simulator.backend.config.security;

import com.trading_simulator.backend.object.entity.ApiPermission;
import com.trading_simulator.backend.object.entity.ApiPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${LOCALHOST_VUE_URL}")
    private String LOCALHOST_VUE_URL;

    @Value("${IPV4_VUE_URL}")
    private String IPV4_VUE_URL;

    private final ApiPermissionRepository apiPermissionRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    // Với các method = null, method = "*" hoặc method = "ALL" → quyền sẽ được áp dụng cho mọi HTTP Method
    private HttpMethod resolveHttpMethod(String method) {
        if (method == null || method.equalsIgnoreCase("ALL") || method.equals("*")) {
            return null;
        }
        return HttpMethod.valueOf(method.toUpperCase());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                LOCALHOST_VUE_URL,
                IPV4_VUE_URL
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c -> c.configurationSource(corsConfigurationSource()));
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider);

//        http.authorizeHttpRequests(configure -> configure.anyRequest().permitAll());
        http.authorizeHttpRequests(configure -> {
            configure.requestMatchers("/ws/**").permitAll();
            configure.requestMatchers("/user/**").permitAll();
            configure.requestMatchers("/test/web-socket/**").permitAll();

            List<ApiPermission> permissions = apiPermissionRepository.findByEnabledTrue();
            for (ApiPermission permission : permissions) {
                if (Boolean.TRUE.equals(permission.getEnabled())) {
                    HttpMethod method = resolveHttpMethod(permission.getMethod());
                    String[] roles = permission.getRoleIds() == null || permission.getRoleIds().isEmpty()
                         ? null
                         : permission.getRoleIds().stream()
                             .map(r -> "ROLE_" + r.toUpperCase())
                             .toArray(String[]::new);

                    if (method != null) {
                        if (roles == null) {
                            configure.requestMatchers(method, permission.getPattern()).permitAll();
                        } else {
                            configure.requestMatchers(method, permission.getPattern()).hasAnyAuthority(roles);
                        }
                    } else {
                        if (roles == null) {
                            configure.requestMatchers(permission.getPattern()).permitAll();
                        } else {
                            configure.requestMatchers(permission.getPattern()).hasAnyAuthority(roles);
                        }
                    }
                }
            }
            // Nếu không match → từ chối truy cập
            configure.anyRequest().denyAll();
        });
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
        );
        return http.build();
    }
}