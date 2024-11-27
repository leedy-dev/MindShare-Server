package com.mindshare.security.config;

import com.mindshare.cmm.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final SecurityConfig securityConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        securityConfig.configureCommonSecurity(http);
        // setting
        http
                // auth
                .authorizeHttpRequests(request ->
                        request
                                // h2 db
                                .requestMatchers("/h2-console/**")
                                .permitAll()

                                // swagger
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                                .permitAll()

                                // auth
                                .requestMatchers("/api/auth/login")
                                .permitAll()
                );
        return http.build();
    }

}
