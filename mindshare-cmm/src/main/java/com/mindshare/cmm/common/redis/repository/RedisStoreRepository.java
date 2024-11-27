package com.mindshare.cmm.common.redis.repository;

import com.mindshare.cmm.common.redis.entity.RedisStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RedisStoreRepository extends JpaRepository<RedisStore, String> {

    boolean existsByRedisKeyAndExpiresAtAfter(String key, LocalDateTime localDateTime);

    void deleteAllByExpiresAtBefore(LocalDateTime localDateTime);

}
