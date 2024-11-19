package com.mindshare.security.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class AuthProperties {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.at-exp-sec}")
    private long AT_EXP_SEC;

    @Value("${jwt.rt-exp-sec}")
    private long RT_EXP_SEC;

}
