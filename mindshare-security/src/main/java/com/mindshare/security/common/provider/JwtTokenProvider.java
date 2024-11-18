package com.mindshare.security.common.provider;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.ms-secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.at-exp-sec}")
    private long AT_EXP_SEC;

    @Value("${jwt.rt-exp-sec}")
    private long RT_EXP_SEC;

    private Key key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Access Token 생성
    public String generateAccessToken(String username) {
        return generateToken(username, AT_EXP_SEC * 1000);
    }

    // Refresh Token 생성
    public String generateRefreshToken(String username) {
        return generateToken(username, RT_EXP_SEC * 1000);
    }

    // 토큰 생성
    private String generateToken(String username, long validity) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
