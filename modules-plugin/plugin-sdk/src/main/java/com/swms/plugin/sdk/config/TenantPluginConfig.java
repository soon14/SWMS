package com.swms.plugin.sdk.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.tenant.config.util.TenantContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class TenantPluginConfig {

    private static final Map<String, List<PluginConfig>> tenantConfig = Maps.newConcurrentMap();

    public static void initTenantConfig(List<TenantPluginConfigDTO> tenantPluginConfigDTOS) {
        if (CollectionUtils.isEmpty(tenantPluginConfigDTOS)) {
            return;
        }

        Map<String, List<TenantPluginConfigDTO>> tenantMap = tenantPluginConfigDTOS.stream()
            .collect(Collectors.groupingBy(TenantPluginConfigDTO::getTenantName));

        tenantMap.forEach((key, values) -> {
            List<PluginConfig> pluginConfigs = values.stream().map(v -> PluginConfig.builder().pluginId(v.getPluginCode())
                .pluginInfo(v.getConfigInfo())
                .build()).toList();

            tenantConfig.put(key, pluginConfigs);
        });
    }

    public static <T> T getTenantConfig(String pluginId, Class<T> clazz) {
        List<PluginConfig> pluginConfigs = tenantConfig.get(TenantContext.getTenant());

        if (CollectionUtils.isEmpty(pluginConfigs)) {
            return newClazzInstance(clazz);
        }

        Optional<PluginConfig> pluginConfig = pluginConfigs.stream().filter(v -> StringUtils.equals(v.getPluginId(), pluginId))
            .findFirst();
        if (pluginConfig.isPresent()) {
            return JsonUtils.string2Object(pluginConfig.get().getPluginInfo(), clazz);
        } else {
            return newClazzInstance(clazz);
        }
    }

    public static void updateTenantConfig(TenantPluginConfigDTO tenantPluginConfigDTO) {
        if (tenantPluginConfigDTO == null) {
            return;
        }

        PluginConfig pluginConfig = PluginConfig.builder().pluginId(tenantPluginConfigDTO.getPluginCode())
            .pluginInfo(tenantPluginConfigDTO.getConfigInfo())
            .build();

        List<PluginConfig> pluginConfigs = tenantConfig.get(tenantPluginConfigDTO.getTenantName());
        if (CollectionUtils.isEmpty(pluginConfigs)) {
            tenantConfig.put(tenantPluginConfigDTO.getTenantName(), Lists.newArrayList(pluginConfig));
            return;
        }

        Optional<PluginConfig> exitsPluginConfig = pluginConfigs.stream()
            .filter(v -> StringUtils.equals(v.getPluginId(), tenantPluginConfigDTO.getPluginCode()))
            .findFirst();

        if (exitsPluginConfig.isPresent()) {
            exitsPluginConfig.get().setPluginInfo(tenantPluginConfigDTO.getConfigInfo());
        } else {
            pluginConfigs.add(pluginConfig);
        }

    }

    private static <T> T newClazzInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            log.error("no such method exception: ", e);
        } catch (ReflectiveOperationException e) {
            log.error("reflective operation error: ", e);
        }
        throw new WmsException("new clazz:{} instance error,", clazz.getName());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PluginConfig {
        private String pluginId;
        private String pluginInfo;
    }
}
