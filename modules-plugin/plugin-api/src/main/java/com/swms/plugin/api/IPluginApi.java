package com.swms.plugin.api;

import com.swms.plugin.api.dto.TenantPluginConfigDTO;

import java.util.List;
import java.util.Map;

public interface IPluginApi {

    List<String> getStartedTenantPluginIds(String tenantName);

    List<TenantPluginConfigDTO> getPluginConfig(String pluginCode);

    TenantPluginConfigDTO getPluginConfig(String tenantName, String pluginId);
}
