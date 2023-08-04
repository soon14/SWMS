package com.swms.distribute.lock;

public interface DistributeLock {
    boolean acquireLock(String lockKey, long leaseTimeInMillis);

    void releaseLock(String lockKey);

    void acquireLockIfThrows(String lockKey, long leaseTimeInMillis);
}
