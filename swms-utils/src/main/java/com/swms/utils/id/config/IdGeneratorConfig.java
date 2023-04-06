package com.swms.utils.id.config;

import com.swms.utils.id.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Value("${data.center.id:1}")
    private long dataCenterId;

    @Value("${worker.id:1}")
    private long workerId;

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(dataCenterId, workerId);
    }
}
