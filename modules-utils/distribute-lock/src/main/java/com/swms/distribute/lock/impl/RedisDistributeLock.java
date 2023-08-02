package com.swms.distribute.lock.impl;

import com.swms.common.utils.utils.RedisUtils;
import com.swms.distribute.lock.DistributeLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
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
