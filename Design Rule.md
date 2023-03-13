# Design Rule

1. Big transaction is forbidden, use Async and eventually consistent
2. each Module define errorCode by herself , it means there is no common errorCode class which own all errorCodes.
3. common module should not depend on other module, it means common module should not import other module. and common
   module should not contain any domain class.It's just defined a common utils.
