package com.swms.utils.lock;

public interface DistributeLock {
    boolean acquireLock(String lockKey, long leaseTimeInMillis);

    void releaseLock(String lockKey);
}
