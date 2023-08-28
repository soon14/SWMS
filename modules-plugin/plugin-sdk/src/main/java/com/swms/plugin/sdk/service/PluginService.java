package com.swms.plugin.sdk.service;

import com.swms.plugin.api.dto.PluginManageDTO;

import java.io.IOException;

public interface PluginService {

    void install(PluginManageDTO pluginManageDTO) throws IOException;

    void start(PluginManageDTO pluginManageDTO);

    void stop(PluginManageDTO pluginManageDTO);

    void restart(PluginManageDTO pluginManageDTO);

    void uninstall(PluginManageDTO pluginManageDTO);
}
