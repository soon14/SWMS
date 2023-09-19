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
        redissonClient.getBucket(generateKey(key)).set(value);
    }

    public String get(String key) {
        return redissonClient.getBucket(generateKey(key)).get().toString();
    }

    public void delete(String key) {
        redissonClient.getBucket(generateKey(key)).delete();
    }

    public void close() {
        redissonClient.shutdown();
    }

    public void publish(String topic, Object message) {
        redissonClient.getTopic(generateKey(topic), new JsonJacksonCodec()).publish(message);
    }

    public void listen(String topic, Class<?> type, MessageListener listener) {
        redissonClient.getTopic(generateKey(topic), new JsonJacksonCodec()).addListener(type, listener);
    }

    /**
     * lock
     *
     * @param lockKey
     *
     * @return
     */
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(generateKey(lockKey));
    }

    /**
     * redis get and increment
     *
     * @param key
     * @param cacheSize
     *
     * @return
     */
    public long getAndIncrement(String key, int cacheSize) {
        return redissonClient.getAtomicLong(generateKey(key)).getAndAdd(cacheSize);
    }

    /**
     * List push
     *
     * @param key
     * @param t
     * @param <T>
     */
    public <T> void push(String key, T t) {
        redissonClient.getList(generateKey(key)).add(t);
    }

    /**
     * Lst getAll
     *
     * @param key
     * @param <T>
     *
     * @return
     */
    public <T> List<T> getList(String key) {
        RList<T> rList = redissonClient.getList(generateKey(key));
        return rList.range(rList.size());
    }

    /**
     * List remove list
     *
     * @param key
     * @param removeList
     * @param <T>
     */
    public <T> void removeList(String key, List<T> removeList) {
        RList<T> rList = redissonClient.getList(generateKey(key));
        rList.removeAll(removeList);
    }

    /**
     * List add list
     *
     * @param key
     * @param newList
     * @param <T>
     */
    public <T> void pushAll(String key, List<T> newList) {
        redissonClient.getList(generateKey(key)).addAll(newList);
    }


    private String generateKey(String key) {
        return TenantContext.getTenant() + "." + key;
    }

}


