package com.swms.common.utils.utils;

import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnClass(RedissonClient.class)
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

    public long getAndIncrement(String key, int cacheSize) {
        return redissonClient.getAtomicLong(key).getAndAdd(cacheSize);
    }

    public <T> void push(String key, T t) {
        redissonClient.getList(key).add(t);
    }

    public <T> List<T> getList(String key) {
        RList<T> rList = redissonClient.getList(key);
        return rList.range(rList.size());
    }

    public <T> void removeList(String key, List<T> removeList) {
        RList<T> rList = redissonClient.getList(key);
        rList.removeAll(removeList);
    }

    public <T> void pushAll(String key, List<T> newList) {
        redissonClient.getList(key).addAll(newList);
    }
}


