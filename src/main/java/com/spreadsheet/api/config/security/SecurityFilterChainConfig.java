package com.spreadsheet.api.config.security;

import com.spreadsheet.api.model.authentication.Role;
import com.spreadsheet.api.service.authentication.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    private static final String API_V1 = "/api/v1";

    // Whitelist of endpoints that do not require authentication
    private static final RequestPermissions[] AUTH_WHITELIST = {
            new RequestPermissions(HttpMethod.POST, API_V1.concat("/auth/register"), null),
            new RequestPermissions(HttpMethod.GET, API_V1.concat("/auth/login"), null),
    };

    // Custom endpoints that require a specific role
    private static final RequestPermissions[] AUTH_CUSTOMS = {
            new RequestPermissions(HttpMethod.GET, API_V1.concat("/user/all"), Role.ADMIN.name()),
            new RequestPermissions(HttpMethod.DELETE, API_V1.concat("/user"), Role.ADMIN.name()),
    };

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> {
                    for (RequestPermissions request : AUTH_WHITELIST) {
                        r.requestMatchers(request.httpMethod(), request.endpoint()).permitAll();
                    }
                    for (RequestPermissions request : AUTH_CUSTOMS) {
                        r.requestMatchers(request.httpMethod(), request.endpoint()).hasRole(request.minimumRole());
                    }
                    r.anyRequest().authenticated();
                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
