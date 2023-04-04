package com.swms.wms.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class RedisConfiguration {
//
//    @Bean
//    public RedissonClient redissonClient() throws IOException {
//        final Config config = Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
//        config.setCodec(new StringCodec(StandardCharsets.UTF_8));
//        config.useClusterServers();
//        return Redisson.create(config);
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(RedissonClient redissonClient) {
//        return new RedissonConnectionFactory(redissonClient);
//    }
//
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
//        final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }
}

