package com.trading_simulator.backend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final AuthenticationProvider authenticationProvider;

    @Value("${VUE_URL}")
    private String VUE_URL;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(VUE_URL));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    // Bỏ cái này, chuyển sang dùng cái khác vì cái này không áp dụng với mongo
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, active from auth where email = ?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select auth.email, role.id " +
                        "from auth join role on auth.role = role.id " +
                        "where auth.email = ?"
        );
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c -> c.configurationSource(corsConfigurationSource()));
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        http.authenticationProvider(authenticationProvider);

        http.authorizeHttpRequests(configure -> configure
                .requestMatchers("/ws/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                // Sử dụng dữ liệu trong ApiPermission
        );
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
        );

        return http.build();
    }
}
