# Design Rule

1. Big transaction is forbidden, use Async and eventually consistent
2. each Module define errorCode by herself , it means there is no common errorCode class which own all errorCodes.
