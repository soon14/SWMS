# Design Rule

1. Big transaction is forbidden, use Async and eventually consistent.
2. Each Module define errorCode by herself , it means there is no common errorCode class which own all errorCodes.
3. Module swms-utils should not depend on other module, it means common module should not import other module. and
   swms-utils module should not contain any domain class.It's just defined a common utils.
4. Must not use RedisTemplate to operate redis, use RedisUtil instead.
