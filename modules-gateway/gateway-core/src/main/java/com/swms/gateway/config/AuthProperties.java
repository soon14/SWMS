package com.swms.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    /**
     * 忽略URL，List列表形式
     */
    private List<String> ignoreUrl = new ArrayList<>();

    /**
     * 是否启用网关鉴权模式
     */
//    private Boolean enable = true;

    /**
     * 网关鉴权超时续时时长
     */
    private long extendTimeSecond;

    /**
     * JWT签名KEY
     */
    private String signingKey = "defaultSigningKey";

    /**
     * Some url needed check for login
     */
    private static final String[] ENDPOINTS = {
        "/oauth/**",
        "/actuator/**",
        "/v2/api-docs/**",
        "/v2/api-docs-ext/**",
        "/swagger/api-docs",
        "/swagger-ui.html",
        "/doc.html",
        "/swagger-resources/**",
        "/druid/**",
        "/user/api/auth/signin"
    };

    /**
     * 自定义getter方法，并将ENDPOINTS加入至忽略URL列表
     *
     * @return List
     */
    public List<String> getIgnoreUrl() {
        if (!ignoreUrl.contains("/doc.html")) {
            Collections.addAll(ignoreUrl, ENDPOINTS);
        }
        return ignoreUrl;
    }


}
