package com.swms.plugin.sdk.service.impl;

import com.swms.common.utils.exception.WmsException;
import com.swms.distribute.file.client.FastdfsClient;
import com.swms.plugin.api.dto.PluginManageDTO;
import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.sdk.config.TenantPluginConfig;
import com.swms.plugin.sdk.facade.PluginFacade;
import com.swms.plugin.sdk.service.PluginService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.pf4j.AbstractPluginManager;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class PluginServiceImpl implements PluginService {

    private static final String JAR = ".jar";

    @Autowired
    private PluginManager pluginManager;

    @Autowired
    private FastdfsClient fastdfsClient;

    @Autowired
    private PluginFacade pluginFacade;

    @Override
    public void install(PluginManageDTO pluginManageDTO) throws IOException {

        //1. download file from  file server
        String installFilePath = downloadJar(pluginManageDTO, pluginManageDTO.getPluginJarPath());

        //2. install plugin
        String pluginId = pluginManager.loadPlugin(Paths.get(installFilePath));
        if (pluginId == null) {
            throw new WmsException("install plugin:" + pluginManageDTO.getPluginId() + " error");
        }

        pluginManager.startPlugin(pluginId);

    }

    private String downloadJar(PluginManageDTO pluginManageDTO,
                               String filePath) throws IOException {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        byte[] fileBytes = fastdfsClient.download(filePath);
        String fileName = generateFileName(pluginManageDTO.getPluginId(), pluginManageDTO.getVersion());
        File file = createFile(Paths.get(getPluginDir() + File.separator + fileName).toAbsolutePath().toString());
        FileUtils.writeByteArrayToFile(file, fileBytes);
        return file.getAbsolutePath();
    }

    private String getPluginDir() {
        return pluginManager.isDevelopment() ? AbstractPluginManager.DEVELOPMENT_PLUGINS_DIR : AbstractPluginManager.DEFAULT_PLUGINS_DIR;
    }

    private String generateFileName(String pluginId, String version) {
        return pluginId + "-" + version + JAR;
    }

    @Override
    public void start(PluginManageDTO pluginManageDTO) {
        PluginState pluginState = pluginManager.startPlugin(pluginManageDTO.getPluginId());
        if (pluginState != PluginState.STARTED) {
            throw new WmsException("start plugin:" + pluginManageDTO.getPluginId() + " error");
        }
    }

    @Override
    public void stop(PluginManageDTO pluginManageDTO) {
        PluginState pluginState = pluginManager.stopPlugin(pluginManageDTO.getPluginId());
        if (pluginState != PluginState.STOPPED) {
            throw new WmsException("stop plugin:" + pluginManageDTO.getPluginId() + " error");
        }
    }

    @Override
    public void restart(PluginManageDTO pluginManageDTO) {
        stop(pluginManageDTO);
        start(pluginManageDTO);
    }

    @Override
    public void uninstall(PluginManageDTO pluginManageDTO) {
        pluginManager.unloadPlugin(pluginManageDTO.getPluginId());
    }

    @Override
    public void configure(PluginManageDTO pluginManageDTO) {
        PluginWrapper plugin = pluginManager.getPlugin(pluginManageDTO.getPluginId());
        if (plugin != null) {
            TenantPluginConfigDTO tenantPluginConfigDTO = pluginFacade.getConfigJsonString(pluginManageDTO.getTenantName(), pluginManageDTO.getPluginId());
            TenantPluginConfig.updateTenantConfig(tenantPluginConfigDTO);
        }
    }


    public static File createFile(String path) throws IOException {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file;
            } else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    throw new IOException("Create " + parentFile + " dir error");
                } else if (file.createNewFile()) {
                    return file;
                } else {
                    throw new IOException("Create " + path + " file error");
                }
            }
        } catch (Exception var3) {
            throw new IOException("Create " + path + " file error");
        }
    }
}
