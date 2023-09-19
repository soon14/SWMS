package com.swms.distribute.lock.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.CommonErrorDescEnum;
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
    public boolean acquireLock(String lockKey, long waitTimeInMillis) {
        RLock lock = redisUtils.getLock(lockKey);
        try {
            return lock.tryLock(waitTimeInMillis, 600000, TimeUnit.MILLISECONDS);
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

    @Override
    public void acquireLockIfThrows(String lockKey, long waitTimeInMillis) {
        boolean acquireLock = acquireLock(lockKey, waitTimeInMillis);
        if (!acquireLock) {
            throw WmsException.throwWmsException(CommonErrorDescEnum.REPEAT_REQUEST);
        }
    }

    @Override
    public void acquireLockIfThrows(String lockKey) {
        acquireLockIfThrows(lockKey, 3000L);
    }
}
