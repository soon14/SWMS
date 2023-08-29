package com.swms.plugin.sdk.configuration;

import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PluginConfiguration {
    @Bean
    public SpringPluginManager pluginManager() {
        SpringPluginManager springPluginManager = new SpringPluginManager();
//        springPluginManager.loadPlugins();
        return springPluginManager;
    }
}
