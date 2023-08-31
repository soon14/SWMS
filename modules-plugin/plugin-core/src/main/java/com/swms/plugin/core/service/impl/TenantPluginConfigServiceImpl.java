package com.swms.plugin.core.service.impl;

import com.swms.common.utils.constants.RedisConstants;
import com.swms.mq.MqClient;
import com.swms.plugin.api.constants.PluginManageTypeEnum;
import com.swms.plugin.api.dto.PluginManageDTO;
import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.core.model.entity.TenantPluginConfig;
import com.swms.plugin.core.model.repository.TenantPluginConfigRepository;
import com.swms.plugin.core.service.TenantPluginConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantPluginConfigServiceImpl implements TenantPluginConfigService {

    @Autowired
    private TenantPluginConfigRepository tenantPluginConfigRepository;

    @Autowired
    private MqClient mqClient;

    @Override
    public void saveConfig(TenantPluginConfigDTO tenantPluginConfigDTO) {
        TenantPluginConfig tenantPluginConfig = new TenantPluginConfig();
        tenantPluginConfig.setPluginCode(tenantPluginConfigDTO.getPluginCode());
        tenantPluginConfig.setTenantName(tenantPluginConfigDTO.getTenantName());
        tenantPluginConfig.setConfigInfo(tenantPluginConfig.getConfigInfo());
        tenantPluginConfigRepository.save(tenantPluginConfig);

        PluginManageDTO pluginManageDTO = PluginManageDTO.builder()
            .pluginManageType(PluginManageTypeEnum.CONFIG)
            .pluginId(tenantPluginConfigDTO.getPluginCode())
            .tenantName(tenantPluginConfigDTO.getTenantName()).build();
        mqClient.sendMessage(RedisConstants.PLUGIN_LISTEN_PLUGIN_MANAGEMENT, pluginManageDTO);
    }

    @Override
    public void updateConfig(TenantPluginConfigDTO tenantPluginConfigDTO) {
        TenantPluginConfig tenantPluginConfig = tenantPluginConfigRepository
            .findById(tenantPluginConfigDTO.getId()).orElseThrow();
        tenantPluginConfig.setConfigInfo(tenantPluginConfigDTO.getConfigInfo());
        tenantPluginConfigRepository.save(tenantPluginConfig);

        PluginManageDTO pluginManageDTO = PluginManageDTO.builder()
            .pluginManageType(PluginManageTypeEnum.CONFIG)
            .pluginId(tenantPluginConfig.getPluginCode())
            .tenantName(tenantPluginConfig.getTenantName()).build();
        mqClient.sendMessage(RedisConstants.PLUGIN_LISTEN_PLUGIN_MANAGEMENT, pluginManageDTO);
    }

    @Override
    public TenantPluginConfig getTenantConfig(String tenantName, String pluginCode) {
        return tenantPluginConfigRepository.findByTenantNameAndPluginCode(tenantName, pluginCode);
    }
}
