package com.swms.plugin.sdk.utils;

import com.swms.plugin.sdk.facade.PluginFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Component
public class PluginUtils {

    @Autowired
    private PluginFacade pluginFacade;

    @Autowired
    private PluginManager pluginManager;

    public <T> List<T> getExtractObject(Class<T> tClass) {

        List<String> pluginIds = pluginFacade.getStartedTenantPluginIds();

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
