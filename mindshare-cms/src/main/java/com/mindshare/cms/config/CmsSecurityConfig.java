package com.mindshare.cms.config;

import com.mindshare.core.common.enums.UserTypes;
import com.mindshare.core.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class CmsSecurityConfig {

    private final SecurityConfig securityConfig;

    @Bean
    public SecurityFilterChain cmsFilterChain(HttpSecurity http) throws Exception {
        securityConfig.configureCommonSecurity(http);
        http
                .securityMatcher("/cms/**")
                .authorizeHttpRequests(request ->
                        request 
                                // board
                                .requestMatchers("/api/board/**")
                                .hasAnyAuthority(UserTypes.MEMBER.getKey(), UserTypes.ADMIN.getKey())

                                // any request
                                .anyRequest()
                                .authenticated()
                );

        return http.build();
    }

}