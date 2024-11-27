package com.mindshare.cmm.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class AuthProperties {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.at-exp-sec}")
    private long atExpSec;

    @Value("${jwt.rt-exp-sec}")
    private long rtExpSec;

}
