package com.swms.plugin.core.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PluginDTO {

    private Long id;

    @NotEmpty
    @Size(max = 64)
    private String code;

    @NotEmpty
    @Size(max = 128)
    private String name;

    @NotEmpty
    @Size(max = 64)
    private String developer;

    @NotEmpty
    @Size(max = 64)
    private String version;

    @NotEmpty
    @Size(max = 32)
    private String system;

    @NotEmpty
    @Size(max = 32)
    private String module;

    @Size(max = 128)
    private String dependencies;

    @Size(max = 256)
    private String description;

    @Size(max = 256)
    private String systemRequest;

    @Size(max = 256)
    private String jarFilePath;

    @Size(max = 256)
    private String configFilePath;
}
