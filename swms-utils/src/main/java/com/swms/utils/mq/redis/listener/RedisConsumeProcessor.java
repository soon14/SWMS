package com.swms.utils.mq.redis.listener;

import com.swms.utils.mq.redis.RedisListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Slf4j
@Component
public class RedisConsumeProcessor implements BeanPostProcessor {

    @Autowired
    private RedissonClient redissonClient;

    @Value("${mq.type:redis}")
    private String mqType;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!StringUtils.equals("redis", mqType)) {
            return bean;
        }

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            RedisListener mqConsumer = AnnotationUtils.findAnnotation(method, RedisListener.class);
            if (null != mqConsumer && StringUtils.isNotEmpty(mqConsumer.topic())) {
                redissonClient.getTopic(mqConsumer.topic(), new JsonJacksonCodec()).addListener(mqConsumer.type(),
                    (channel, msg) -> {
                        log.debug("get message: {} from topic: {}", msg, channel);
                        try {
                            method.invoke(bean, String.valueOf(channel), msg);
                        } catch (Exception e) {
                            log.error("redis topic: {} consume error: ", channel, e);
                        }
                    });
            }
        }
        return bean;
    }

}