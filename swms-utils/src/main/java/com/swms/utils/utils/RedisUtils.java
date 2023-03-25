package com.swms.utils.utils;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    @Autowired
    private RedissonClient redissonClient;

    public void set(String key, String value) {
        redissonClient.getBucket(key).set(value);
    }

    public String get(String key) {
        return redissonClient.getBucket(key).get().toString();
    }

    public void delete(String key) {
        redissonClient.getBucket(key).delete();
    }

    public void close() {
        redissonClient.shutdown();
    }
}


