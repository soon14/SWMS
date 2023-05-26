package com.swms.gateway.auth;

import com.swms.gateway.config.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Autowired
    private AuthProperties authProperties;

    @Override
    public GatewayFilter apply(Object config) {
        return new AuthGatewayFilter(authProperties);
    }
}
