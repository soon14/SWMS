package com.swms.plugin.core.application;

import com.swms.plugin.api.IPluginApi;
import com.swms.plugin.api.constants.TenantPluginStatusEnum;
import com.swms.plugin.core.model.entity.TenantInstallPlugin;
import com.swms.plugin.core.model.repository.TenantInstallPluginRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class PluginApiImpl implements IPluginApi {

    @Autowired
    private TenantInstallPluginRepository tenantInstallPluginRepository;

    @Override
    public List<String> getStartedTenantPluginIds(String tenantName) {
        List<TenantInstallPlugin> tenantPlugins = tenantInstallPluginRepository.findByTenantName(tenantName);
        return tenantPlugins.stream().filter(v -> v.getStatus() == TenantPluginStatusEnum.STARTED)
            .map(TenantInstallPlugin::getPluginCode).toList();
    }
}
