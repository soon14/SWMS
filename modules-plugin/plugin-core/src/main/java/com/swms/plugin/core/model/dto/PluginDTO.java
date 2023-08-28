package com.swms.plugin.core.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private String pluginVersion;

    @NotEmpty
    @Size(max = 32)
    private String applySystem;

    @NotEmpty
    @Size(max = 32)
    private String applyModule;

    @Size(max = 128)
    private String dependencies = "";

    @Size(max = 256)
    private String description = "";

    @Size(max = 256)
    private String systemRequest = "";

    @Size(max = 256)
    private String jarFilePath;

    @Size(max = 256)
    private String configFilePath = "";

    private Long version;

    @NotNull
    private MultipartFile jarFile;

    private MultipartFile configFile;

}
