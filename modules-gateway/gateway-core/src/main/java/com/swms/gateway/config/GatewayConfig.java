package com.swms.gateway.config;

import com.swms.gateway.auth.AuthGatewayFilter;
import com.swms.user.api.utils.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public AuthGatewayFilter authGatewayFilter(AuthProperties authProperties, JwtUtils jwtUtils) {
        return new AuthGatewayFilter(authProperties, jwtUtils);
    }
}
