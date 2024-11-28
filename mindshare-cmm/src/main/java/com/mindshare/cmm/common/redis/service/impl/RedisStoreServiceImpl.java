package com.mindshare.cmm.common.redis.service.impl;

import com.mindshare.cmm.common.redis.entity.RedisStore;
import com.mindshare.cmm.common.redis.repository.RedisStoreRepository;
import com.mindshare.cmm.common.redis.service.RedisStoreService;
import com.mindshare.core.common.utils.CommonObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisStoreServiceImpl implements RedisStoreService {

    private final RedisStoreRepository redisStoreRepository;

    @Override
    @Transactional
    public void save(String key, String value, long ttlSec) {
        // build key-value
        RedisStore redisStore = RedisStore.builder()
                .redisKey(key)
                .redisValue(value)
                .expiresAt(LocalDateTime.now().plusSeconds(ttlSec))
                .build();

        // save
        redisStoreRepository.save(redisStore);
    }

    @Override
    @Transactional
    public boolean saveIfAbsent(String key, String value, long ttlSec) {
        if (CommonObjectUtils.isNull(get(key))) {
            save(key, value, ttlSec);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasKey(String key) {
        return redisStoreRepository.existsByRedisKeyAndExpiresAtAfter(key, LocalDateTime.now());
    }

    @Override
    @Transactional
    public String get(String key) {
        // key-value 조회
        Optional<RedisStore> redisStoreOp = redisStoreRepository.findById(key);
        LocalDateTime now = LocalDateTime.now();

        // 존재할 경우
        if (redisStoreOp.isPresent()){
            RedisStore redisStore = redisStoreOp.get();

            // 만료시간 지났을 경우 삭제
            if (redisStore.getExpiresAt().isBefore(now)) {
                redisStoreRepository.deleteById(key);
                return null;
            }
            // value 리턴
            else {
                return redisStore.getRedisValue();
            }
        }
        // 존재하지 않을 경우
        else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(String key) {
        redisStoreRepository.deleteById(key);
    }

    @Override
    @Transactional
    public void cleanExpiredKey() {
        redisStoreRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());
    }

}
