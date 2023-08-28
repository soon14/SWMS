package com.swms.plugin.sdk.service.impl;

import com.gitee.starblues.core.PluginInfo;
import com.gitee.starblues.integration.application.PluginApplication;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.utils.PluginFileUtils;
import com.swms.common.utils.exception.WmsException;
import com.swms.distribute.file.client.FastdfsClient;
import com.swms.plugin.api.dto.PluginManageDTO;
import com.swms.plugin.sdk.service.PluginService;
import com.swms.plugin.sdk.utils.SignatureUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PluginServiceImpl implements PluginService {

    private static final String JAR = ".jar";

    private static final String YML = ".yml";

    private static final String PROPERTIES = ".properties";

    private static final String DIR_PLUGINS = "plugins";

    @Autowired
    private PluginOperator pluginOperator;

    @Autowired
    private FastdfsClient fastdfsClient;

    @Override
    public void install(PluginManageDTO pluginManageDTO) throws IOException {

        //1. download file from  file server
        String installFilePath = downloadJar(pluginManageDTO, 0, pluginManageDTO.getPluginJarPath());
        downloadJar(pluginManageDTO, 1, pluginManageDTO.getPluginConfigPath());

        //2. install plugin
        PluginInfo pluginInfo = pluginOperator.install(Paths.get(installFilePath), false);
        if (pluginInfo == null) {
            throw new WmsException("install plugin:" + pluginManageDTO.getPluginId() + " error");
        }

    }

    private String downloadJar(PluginManageDTO pluginManageDTO, int type,
                               String filePath) throws IOException {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        byte[] fileBytes = fastdfsClient.download(filePath);
        String fileName = generateFileName(type, pluginManageDTO.getPluginId(), pluginManageDTO.getVersion());

        String configPath = DIR_PLUGINS + File.separator + pluginManageDTO.getPluginId()
            + File.separator + pluginManageDTO.getVersion() + File.separator + fileName;
        File newFile = PluginFileUtils.createExistFile(Paths.get(configPath));
        Files.write(newFile.getAbsoluteFile().toPath(), fileBytes);

        return newFile.getAbsolutePath();
    }

    private String generateFileName(int type, String pluginId, String version) {

        String fileName = "";
        //插件强制命名如下形式：pluginId-1.0.0.jar
        if (type == 0) {
            String signatrue = SignatureUtil.doSign(pluginId + version);
            fileName = pluginId + "(" + signatrue.substring(0, 5) + ")" + "-" + version + JAR;
        } else {
            if (StringUtils.equals(fileName, YML)) {
                fileName = pluginId + YML;
            } else {
                fileName = pluginId + PROPERTIES;
            }
        }
        return fileName;

    }

    @Override
    public void start(PluginManageDTO pluginManageDTO) {
        boolean result = pluginOperator.start(pluginManageDTO.getPluginId());
        if (!result) {
            throw new WmsException("start plugin:" + pluginManageDTO.getPluginId() + " error");
        }
    }

    @Override
    public void stop(PluginManageDTO pluginManageDTO) {
        boolean result = pluginOperator.stop(pluginManageDTO.getPluginId());
        if (!result) {
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
        pluginOperator.uninstall(pluginManageDTO.getPluginId(), true, true);
    }

}
