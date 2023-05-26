package com.swms.gateway.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author guizhigang
 * @Date 2021/5/17 15:26
 * @Description:
 */
@Data
@Configuration
@RefreshScope
public class AuthProperties {

    /**
     * 忽略URL，List列表形式
     */
    @NacosValue(value = "${auth.ignore-url}", autoRefreshed = true)
    private List<String> ignoreUrl = new ArrayList<>();

    /**
     * 是否启用网关鉴权模式
     */
    @NacosValue(value = "${auth.enable}", autoRefreshed = true)
    private Boolean enable = true;

    /**
     * 网关鉴权超时续时时长
     */
    @NacosValue(value = "${auth.extend-time-second:3600}", autoRefreshed = true)
    private long extendTimeSecond;

    /**
     * JWT签名KEY
     */
    @NacosValue(value = "${auth.signing-key}", autoRefreshed = true)
    private String signingKey = "defaultSigningKey";

    /**
     * 监控中心和swagger需要访问的url
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
        "/webjars/**",
        "/druid/**",
        "/error/**",
        "/assets/**",
        "/auth/logout",
        "/auth/code",
        "/user/config.js"
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
