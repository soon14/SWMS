package com.swms.plugin.api;

import java.util.List;

public interface IPluginApi {

    List<String> getTenantPluginIds(String tenantName);
}
