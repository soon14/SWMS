package com.swms.utils.mq;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqClient {

    @Value("${mq.type:redis}")
    private String mqType;

    @Autowired
    private RedissonClient redissonClient;

    public void sendMessage(String topic, Object message) {

        if (mqType.equals("rabbitmq")) {
            log.info("rabbitmq");
        } else if (mqType.equals("kafka")) {
            log.info("kafka");
        } else if (mqType.equals("redis")) {
            redissonClient.getTopic(topic, new JsonJacksonCodec()).publish(message);
        }
    }
}
