package com.mindshare.security.common.redis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Redis 인것처럼 동작하기 위한 Entity
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RedisStore {

    @Id
    private String redisKey;

    private String redisValue;

    private LocalDateTime expiresAt;

}
