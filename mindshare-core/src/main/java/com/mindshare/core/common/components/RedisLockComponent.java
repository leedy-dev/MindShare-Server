package com.mindshare.core.common.components;

import com.mindshare.core.common.redis.service.RedisStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisLockComponent {

    private final RedisStoreService redisStoreService;

    public String tryLock(String key, long ttlSec) {
        String lockId = UUID.randomUUID().toString();
        boolean success = redisStoreService.saveIfAbsent(key, lockId, ttlSec);
        return success ? lockId : null;
    }

    public void unlock(String key, String lockId) {
        String currentLockId = redisStoreService.get(key);
        if (lockId.equals(currentLockId)) {
            redisStoreService.delete(key);
        }
    }

    public boolean isLocked(String key) {
        return redisStoreService.hasKey(key);
    }

}
