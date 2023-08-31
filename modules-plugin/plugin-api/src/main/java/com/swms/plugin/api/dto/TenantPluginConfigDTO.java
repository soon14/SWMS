package com.swms.plugin.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TenantPluginConfigDTO implements Serializable {

    private Long id;

    private String tenantName;

    @NotEmpty
    private String pluginCode;

    @NotEmpty
    private String configInfo;

    private Long version;
}
