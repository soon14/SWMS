package com.swms.utils.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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

    public void publish(String topic, Object message) {
        redissonClient.getTopic(topic, new JsonJacksonCodec()).publish(message);
    }

    public void listen(String topic, Class<?> type, MessageListener listener) {
        redissonClient.getTopic(topic, new JsonJacksonCodec()).addListener(type, listener);
    }

    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }
}


