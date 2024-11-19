package com.mindshare.ams.config;

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
    public SecurityFilterChain amsFilterChain(HttpSecurity http) throws Exception {
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
