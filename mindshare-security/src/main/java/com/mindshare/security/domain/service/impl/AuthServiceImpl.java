package com.mindshare.security.domain.service.impl;

import com.mindshare.core.common.properties.AuthProperties;
import com.mindshare.core.common.provider.JwtTokenProvider;
import com.mindshare.core.common.redis.service.RedisStoreService;
import com.mindshare.security.domain.service.AuthService;
import com.mindshare.security.domain.service.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisStoreService redisStoreService;
    private final AuthProperties authProperties;

    @Override
    public AuthDto.Token login(AuthDto.Login authDto) {
        // get uid
        String uid = authDto.getUid();

        // 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(uid, authDto.getPassword())
        );

        // 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(uid);
        String refreshToken = jwtTokenProvider.generateRefreshToken(uid);

        // redis에 저장
        // TODO: 비동기로 실행
        redisStoreService.save(uid, refreshToken, authProperties.getRtExpSec());

        return AuthDto.Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
