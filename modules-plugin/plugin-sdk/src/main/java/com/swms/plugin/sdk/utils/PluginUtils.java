package com.swms.plugin.sdk.utils;

import com.swms.plugin.api.IPluginApi;
import com.swms.tenant.config.util.TenantContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PluginUtils {

    @DubboReference
    private IPluginApi iPluginApi;

    @Autowired
    private PluginManager pluginManager;

    public <T> List<T> getExtractObject(Class<T> tClass) {

        String tenant = TenantContext.getTenant();
        List<String> pluginIds = iPluginApi.getStartedTenantPluginIds(tenant);

        if (CollectionUtils.isEmpty(pluginIds)) {
            return Collections.emptyList();
        }

        List<T> extractObjects = pluginIds.stream()
            .map(pluginId -> pluginManager.getExtensions(tClass, pluginId))
            .flatMap(Collection::stream)
            .filter(Objects::nonNull).toList();

        if (CollectionUtils.isNotEmpty(extractObjects)) {
            return extractObjects;
        }
        return Collections.emptyList();
    }
}
