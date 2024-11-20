package com.mindshare.ams.config;

import com.mindshare.core.common.enums.UserTypes;
import com.mindshare.core.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class AmsSecurityConfig {

    private final SecurityConfig securityConfig;

    @Bean
    public SecurityFilterChain amsFilterChain(HttpSecurity http) throws Exception {
        securityConfig.configureCommonSecurity(http);

        http
                .securityMatcher("/ams/**")
                .authorizeHttpRequests(request ->
                        request
                                // user
                                .requestMatchers("/api/user/**")
                                .hasAnyAuthority(UserTypes.MEMBER.getKey(), UserTypes.ADMIN.getKey())

                                // any request
                                .anyRequest()
                                .authenticated()
                );

        return http.build();
    }
}
