package com.swms.plugin.core.service;

import com.swms.plugin.api.dto.PluginDTO;
import com.swms.plugin.core.model.entity.Plugin;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;

@Validated
public interface PluginManagementService {

    void addPlugin(@Valid PluginDTO pluginDTO) throws IOException;

    List<Plugin> getByCode(String code);

    Plugin getByCodeAndVersion(String code, String version);

    void delete(Long pluginId);
}
