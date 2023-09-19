package com.swms.plugin.sdk.facade;

import com.swms.plugin.api.IPluginApi;
import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.common.utils.utils.TenantContext;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PluginFacade {

    @DubboReference
    private IPluginApi iPluginApi;

    public List<String> getStartedTenantPluginIds() {
        String tenant = TenantContext.getTenant();
        return iPluginApi.getStartedTenantPluginIds(tenant);
    }

    public List<TenantPluginConfigDTO> getConfigJsonString(String pluginId) {
        return iPluginApi.getPluginConfig(pluginId);
    }


    public TenantPluginConfigDTO getConfigJsonString(String tenantName, String pluginId) {
        return iPluginApi.getPluginConfig(tenantName, pluginId);
    }
}
