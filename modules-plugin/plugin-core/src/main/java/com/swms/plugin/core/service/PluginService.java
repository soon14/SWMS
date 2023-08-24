package com.swms.plugin.core.service;

import com.swms.plugin.core.model.dto.PluginDTO;
import com.swms.plugin.core.model.entity.Plugin;

import java.util.List;

public interface PluginService {

    /**
     * 添加
     *
     * @param param 添加参数
     *
     * @throws Exception 添加异常
     */
    void add(PluginDTO param);

    /**
     * 通过Code获取插件
     *
     * @param code 插件标识
     *
     * @return 插件
     */
    List<Plugin> getByCode(String code);

    /**
     * 通过Code获取插件
     *
     * @param code 插件标识
     *
     * @return 插件
     */
    Plugin getByCodeAndVersion(String code, String version);

    /**
     * 删除插件
     *
     * @param pluginId 插件id
     */
    void delete(Long pluginId);
}
