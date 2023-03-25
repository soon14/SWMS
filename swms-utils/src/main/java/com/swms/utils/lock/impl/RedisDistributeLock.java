package com.swms.utils.lock.impl;

import com.swms.utils.lock.DistributeLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisDistributeLock implements DistributeLock {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean acquireLock(String lockKey, long leaseTimeInMillis) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(0, leaseTimeInMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public void releaseLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
}
