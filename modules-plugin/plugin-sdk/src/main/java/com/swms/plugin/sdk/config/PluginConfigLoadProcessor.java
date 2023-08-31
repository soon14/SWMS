package com.swms.plugin.sdk.config;

import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.sdk.facade.PluginFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Slf4j
public class PluginConfigLoadProcessor {

    private AnnotationConfigApplicationContext applicationContext;

    private String pluginId;

    private List<Class<?>> configClasses;

    private ApplicationContext springApplicationContext;

    public PluginConfigLoadProcessor(List<Class<?>> classes, AnnotationConfigApplicationContext applicationContext, PluginWrapper wrapper) {
        this.configClasses = classes;
        this.applicationContext = applicationContext;
        this.pluginId = wrapper.getPluginId();
        this.springApplicationContext = ((SpringPluginManager) wrapper.getPluginManager()).getApplicationContext();
    }

    public void loadConfiguration() {

        if (CollectionUtils.isEmpty(configClasses)) {
            return;
        }

        PluginFacade pluginFacade = springApplicationContext.getBean(PluginFacade.class);
        List<TenantPluginConfigDTO> tenantConfigs = pluginFacade.getConfigJsonString(pluginId);
        TenantPluginConfig.initTenantConfig(tenantConfigs);
    }
}
