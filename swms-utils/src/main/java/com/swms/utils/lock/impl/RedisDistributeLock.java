package com.swms.utils.lock.impl;

import com.swms.utils.lock.DistributeLock;
import com.swms.utils.utils.RedisUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnClass(RedisUtils.class)
public class RedisDistributeLock implements DistributeLock {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean acquireLock(String lockKey, long leaseTimeInMillis) {
        RLock lock = redisUtils.getLock(lockKey);
        try {
            return lock.tryLock(0, leaseTimeInMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public void releaseLock(String lockKey) {
        RLock lock = redisUtils.getLock(lockKey);
        lock.unlock();
    }
}
