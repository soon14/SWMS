package com.swms.plugin.sdk.utils;

import com.gitee.starblues.spring.extract.ExtractCoordinate;
import com.gitee.starblues.spring.extract.ExtractFactory;
import com.swms.plugin.api.IPluginApi;
import com.swms.tenant.config.util.TenantContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class PluginUtils {

    @DubboReference
    private IPluginApi iPluginApi;

    public <T> T getExtractObject(Class<T> tClass) {

        String tenant = TenantContext.getTenant();
        List<String> pluginIds = iPluginApi.getTenantPluginIds(tenant);

        if (CollectionUtils.isEmpty(pluginIds)) {
            return null;
        }

        List<T> extractObjects = pluginIds.stream().map(pluginId ->
                ExtractFactory.getInstant().getExtractByInterClass(pluginId, tClass))
            .flatMap(Collection::stream)
            .filter(Objects::nonNull).toList();

        if (CollectionUtils.isNotEmpty(extractObjects)) {
            return extractObjects.iterator().next();
        }
        return null;
    }

    public static <T> T getExtractObject(String pluginId, Class<T> tClass) {
        List<T> extractObject = ExtractFactory.getInstant().getExtractByInterClass(pluginId, tClass);

        if (CollectionUtils.isNotEmpty(extractObject)) {
            return extractObject.iterator().next();
        }
        return null;
    }

    public static <T> T getExtractObject(String pluginId, ExtractCoordinate coordinate) {
        try {
            return ExtractFactory.getInstant().getExtractByCoordinate(pluginId, coordinate);
        } catch (Exception e) {
            return null;
        }
    }
}
