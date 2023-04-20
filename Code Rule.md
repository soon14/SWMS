# Code Rule

1. Big transaction is forbidden, use Async and eventually consistent.
2. Each Module define errorCode by herself , it means there is no common errorCode class which own all errorCodes.
3. Module swms-utils should not depend on other module, it means swms-utils module should not import other module. and
   swms-utils module should not contain any domain class.It's just defined a common utils.
4. Must not use RedisTemplate to operate redis, use RedisUtils instead. e.g:
   ```
   redisUtils.get(key);
   ```
5. Use Domain Event to implement asynchronous communication between different domains. e.g:
   ```
   domainEventPublisher.sendAsyncEvent(stockTransferEvent);
   domainEventPublisher.sendAsyncEvent(orderEvent);
   ```
6. Use MqClient to implement asynchronous communication between servers. e.g:
   ```
   mqClient.sendAsyncMessage(stockTransferEvent);
   mqClient.sendAsyncMessage(orderEvent);
   ```
7. Use DistributedLock to implement distributed lock. e.g:
   ```
   distributedLock.acquireLock("lockKey", 1000);
   distributedLock.releaseLock("lockKey");
   ```
8. String type field length must be less than 255, if you want to store more than 255 characters, use text type and
   spilt it into another table.   
