package com.swms.plugin.sdk.config;

import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.sdk.facade.PluginFacade;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Slf4j
public class PluginConfigLoadProcessor {

    private final String pluginId;

    private final ApplicationContext springApplicationContext;

    public PluginConfigLoadProcessor(PluginWrapper wrapper) {
        this.pluginId = wrapper.getPluginId();
        this.springApplicationContext = ((SpringPluginManager) wrapper.getPluginManager()).getApplicationContext();
    }

    public void loadConfiguration() {

        PluginFacade pluginFacade = springApplicationContext.getBean(PluginFacade.class);
        List<TenantPluginConfigDTO> tenantConfigs = pluginFacade.getConfigJsonString(pluginId);
        TenantPluginConfig.initTenantConfig(tenantConfigs);
    }
}
