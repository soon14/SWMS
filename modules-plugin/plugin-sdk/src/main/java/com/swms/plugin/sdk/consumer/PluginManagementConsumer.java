package com.swms.plugin.sdk.consumer;

import com.swms.common.utils.constants.RedisConstants;
import com.swms.mq.redis.RedisListener;
import com.swms.plugin.api.dto.PluginManageDTO;
import com.swms.plugin.sdk.service.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PluginManagementConsumer {

    @Autowired
    private PluginService pluginService;

    @RedisListener(topic = RedisConstants.PLUGIN_LISTEN_PLUGIN_MANAGEMENT, type = PluginManageDTO.class)
    public void listPluginManage(String topic, PluginManageDTO pluginManageDTO) throws IOException {
        if (pluginManageDTO == null) {
            return;
        }

        switch (pluginManageDTO.getPluginManageType()) {
            case INSTALL:
                pluginService.install(pluginManageDTO);
                break;
            case START:
                pluginService.start(pluginManageDTO);
                break;
            case STOP:
                pluginService.stop(pluginManageDTO);
                break;
            case RESTART:
                pluginService.restart(pluginManageDTO);
                break;
            case UNINSTALL:
                pluginService.uninstall(pluginManageDTO);
                break;
            default:
                break;
        }

    }
}
