package com.swms.plugin.core.service.impl;

import com.google.common.base.Preconditions;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.user.UserContext;
import com.swms.plugin.core.model.dto.PluginDTO;
import com.swms.plugin.core.model.entity.Plugin;
import com.swms.plugin.core.model.repository.PluginRepository;
import com.swms.plugin.core.model.repository.TenantInstallPluginRepository;
import com.swms.plugin.core.service.PluginManagementService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PluginManagementServiceImpl implements PluginManagementService {

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private TenantInstallPluginRepository tenantInstallPluginRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPlugin(PluginDTO pluginDTO) {

        List<Plugin> list = getByCode(pluginDTO.getCode());
        if (CollectionUtils.isNotEmpty(list)
            && list.stream().anyMatch(u -> StringUtils.equals(u.getPluginVersion(), pluginDTO.getPluginVersion()))) {
            throw new WmsException("plugin code: " + pluginDTO.getCode() + ", and version: " + pluginDTO.getPluginVersion() + "exist.");
        }

        if (CollectionUtils.isNotEmpty(list)) {
            Plugin plugin = list.iterator().next();
            Preconditions.checkState(Objects.equals(plugin.getCreateUser(), UserContext.getCurrentUser()),
                "you didn't have the right permissions.");
        }

        Plugin plugin = new Plugin();
        BeanUtils.copyProperties(pluginDTO, plugin);
        pluginRepository.save(plugin);
    }

    @Override
    public List<Plugin> getByCode(String code) {
        return pluginRepository.findByCode(code);
    }

    @Override
    public Plugin getByCodeAndVersion(String code, String version) {
        return pluginRepository.findByCodeAndVersion(code, version);
    }

    @Override
    public void delete(Long pluginId) {
        Plugin plugin = pluginRepository.findById(pluginId).orElseThrow();
        Preconditions.checkState(Objects.equals(plugin.getCreateUser(), UserContext.getCurrentUser()),
            "you didn't have the right permissions.");
        pluginRepository.deleteById(pluginId);
    }
}
