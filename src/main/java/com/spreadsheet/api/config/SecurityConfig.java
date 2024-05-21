package com.spreadsheet.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET).permitAll()    // Allow all GET requests without authentication
                .requestMatchers(HttpMethod.POST).permitAll()  // Require authentication for POST requests
                .requestMatchers(HttpMethod.PUT).permitAll()   // Require authentication for PUT requests
                .requestMatchers(HttpMethod.DELETE).permitAll() // Require authentication for DELETE requests
                .anyRequest().permitAll()
        ).csrf().disable().httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,12);
    }

}
