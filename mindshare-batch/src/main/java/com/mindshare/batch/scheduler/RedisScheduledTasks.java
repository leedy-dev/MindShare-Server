package com.mindshare.batch.scheduler;

import com.mindshare.cmm.common.redis.service.RedisStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisScheduledTasks {

    private final RedisStoreService redisStoreService;

    /**
     * 매시간 만료된 키 삭제
     */
    @Scheduled(cron = "0 0 * * * *")
    public void cleanExpiredKey() {
        redisStoreService.cleanExpiredKey();
    }

}
