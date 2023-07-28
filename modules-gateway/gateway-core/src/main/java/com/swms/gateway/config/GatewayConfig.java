package com.swms.gateway.config;

import com.swms.gateway.auth.AuthGatewayFilter;
import com.swms.user.api.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public AuthGatewayFilter authGatewayFilter() {
        return new AuthGatewayFilter(authProperties, jwtUtils);
    }
}
