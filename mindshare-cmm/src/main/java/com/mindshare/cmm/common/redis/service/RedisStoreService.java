package com.mindshare.cmm.common.redis.service;

public interface RedisStoreService {

    void save(String key, String value, long ttlSec);

    boolean saveIfAbsent(String key, String value, long ttlSec);

    boolean hasKey(String key);

    String get(String key);

    void delete(String key);

}
