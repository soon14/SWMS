package com.swms.plugin.core.application;

import com.swms.plugin.api.IPluginApi;
import com.swms.plugin.api.constants.TenantPluginStatusEnum;
import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.core.model.entity.TenantPlugin;
import com.swms.plugin.core.model.entity.TenantPluginConfig;
import com.swms.plugin.core.model.repository.TenantPluginConfigRepository;
import com.swms.plugin.core.model.repository.TenantPluginRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@DubboService
public class PluginApiImpl implements IPluginApi {

    @Autowired
    private TenantPluginRepository tenantPluginRepository;

    @Autowired
    private TenantPluginConfigRepository tenantPluginConfigRepository;

    @Override
    public List<String> getStartedTenantPluginIds(String tenantName) {
        List<TenantPlugin> tenantPlugins = tenantPluginRepository.findByTenantName(tenantName);
        return tenantPlugins.stream().filter(v -> v.getStatus() == TenantPluginStatusEnum.STARTED)
            .map(TenantPlugin::getPluginCode).toList();
    }

    @Override
    public List<TenantPluginConfigDTO> getPluginConfig(String pluginCode) {
        List<TenantPluginConfig> tenantPluginConfigs = tenantPluginConfigRepository.findByPluginCode(pluginCode);

        if (CollectionUtils.isEmpty(tenantPluginConfigs)) {
            return Collections.emptyList();
        }
        return tenantPluginConfigs.stream().map(v -> {
            TenantPluginConfigDTO tenantPluginConfigDTO = new TenantPluginConfigDTO();
            BeanUtils.copyProperties(v, tenantPluginConfigDTO);
            return tenantPluginConfigDTO;
        }).toList();
    }

    @Override
    public TenantPluginConfigDTO getPluginConfig(String tenantName, String pluginCode) {
        TenantPluginConfig tenantPluginConfig = tenantPluginConfigRepository.findByTenantNameAndPluginCode(tenantName, pluginCode);
        if (tenantPluginConfig == null) {
            return null;
        }
        TenantPluginConfigDTO tenantPluginConfigDTO = new TenantPluginConfigDTO();
        BeanUtils.copyProperties(tenantPluginConfig, tenantPluginConfigDTO);
        return tenantPluginConfigDTO;
    }
}
