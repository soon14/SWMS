package com.swms.utils.mq;

import com.swms.utils.mq.redis.RedisConsumer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RedisConsumer
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MqConsumer {

}
