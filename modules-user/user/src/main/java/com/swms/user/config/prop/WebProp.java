package com.swms.user.config.prop;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * web 界面配置
 *
 * @author sws
 * @version 1.0
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "web", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class WebProp {

    /**
     * web 界面路径
     */
    @Value("${webLocation:classpath:/store-web/}")
    private String webLocation;

    /**
     * 生产环境下web的config.js 的配置
     */
    private Map<String, Object> config;
}
