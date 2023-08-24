package com.swms.plugin.core.service.impl;

import com.google.common.base.Preconditions;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.user.UserContext;
import com.swms.plugin.core.model.dto.PluginDTO;
import com.swms.plugin.core.model.entity.Plugin;
import com.swms.plugin.core.model.repository.PluginRepository;
import com.swms.plugin.core.service.PluginService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PluginServiceImpl implements PluginService {

    @Autowired
    private PluginRepository pluginRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PluginDTO pluginDTO) {

        List<Plugin> list = getByCode(pluginDTO.getCode());
        if (!CollectionUtils.isEmpty(list)
            && list.stream().anyMatch(u -> u.getVersion().equals(pluginDTO.getVersion()))) {
            throw new WmsException("plugin code: " + pluginDTO.getCode() + ", and version: " + pluginDTO.getVersion() + "exist.");
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
