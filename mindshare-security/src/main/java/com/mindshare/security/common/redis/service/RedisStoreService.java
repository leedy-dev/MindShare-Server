package com.mindshare.security.common.redis.service;

public interface RedisStoreService {

    void save(String key, String value, long ttlSec);

    String get(String key);

    void delete(String key);

}
