package com.mindshare.ams.config;

import com.mindshare.core.common.enums.UserTypes;
import com.mindshare.core.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.POST, "/ams/api/user")
                                .permitAll()

                                // any request
                                .anyRequest()
                                .hasAnyAuthority(UserTypes.ADMIN.getKey())
                );

        return http.build();
    }
}
