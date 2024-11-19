package com.mindshare.cms.config;

import com.mindshare.core.common.enums.UserTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain cmsFilterChain(HttpSecurity http) throws Exception {
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
