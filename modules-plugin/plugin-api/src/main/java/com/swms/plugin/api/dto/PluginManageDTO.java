package com.swms.plugin.api.dto;

import com.swms.plugin.api.constants.PluginManageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PluginManageDTO {

    private PluginManageTypeEnum pluginManageType;

    private String pluginId;
    private String pluginJarPath;
    private String pluginConfigPath;
    private String version;
}
