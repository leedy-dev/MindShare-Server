package com.mindshare.security.common.redis.repository;

import com.mindshare.security.common.redis.entity.RedisStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RedisStoreRepository extends JpaRepository<RedisStore, String> {

    void deleteAllByExpiresAtBefore(LocalDateTime localDateTime);

}
