package com.swms.plugin.core.service;

import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.core.model.entity.TenantPluginConfig;

public interface TenantPluginConfigService {
    void saveConfig(TenantPluginConfigDTO tenantPluginConfigDTO);

    void updateConfig(TenantPluginConfigDTO tenantPluginConfigDTO);

    TenantPluginConfig getTenantConfig(String tenantName, String pluginCode);
}
