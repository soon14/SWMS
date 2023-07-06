package com.swms.gateway.config;

import com.swms.gateway.auth.AuthGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthProperties authProperties;

    @Bean
    public AuthGatewayFilter authGatewayFilter() {
        return new AuthGatewayFilter(authProperties);
    }
}
