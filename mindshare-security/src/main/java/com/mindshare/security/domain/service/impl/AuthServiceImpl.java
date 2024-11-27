package com.mindshare.security.domain.service.impl;

import com.mindshare.cmm.common.properties.AuthProperties;
import com.mindshare.cmm.common.provider.JwtTokenProvider;
import com.mindshare.cmm.common.redis.service.RedisStoreService;
import com.mindshare.security.domain.service.AuthService;
import com.mindshare.security.domain.service.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(
                uid,
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        String refreshToken = jwtTokenProvider.generateRefreshToken(uid);

        // redis에 저장
        redisStoreService.save(uid, refreshToken, authProperties.getRtExpSec());

        return AuthDto.Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
