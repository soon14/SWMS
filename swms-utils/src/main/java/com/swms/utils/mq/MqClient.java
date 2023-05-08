package com.swms.utils.mq;

import com.swms.utils.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqClient {

    @Value("${mq.type:redis}")
    private String mqType;

    @Autowired
    private RedisUtils redisUtils;

    public void sendMessage(String topic, Object message) {

        if (mqType.equals("rabbitmq")) {
            log.info("rabbitmq");
        } else if (mqType.equals("kafka")) {
            log.info("kafka");
        } else if (mqType.equals("redis")) {
            redisUtils.publish(topic, message);
        }
    }
}
