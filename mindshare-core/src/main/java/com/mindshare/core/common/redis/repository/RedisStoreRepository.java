package com.mindshare.core.common.redis.repository;

import com.mindshare.core.common.redis.entity.RedisStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RedisStoreRepository extends JpaRepository<RedisStore, String> {

    boolean existsByRedisKeyAndExpiresAtAfter(String key, LocalDateTime localDateTime);

    void deleteAllByExpiresAtBefore(LocalDateTime localDateTime);

}
