package com.swms.plugin.core.controller;

import com.gitee.starblues.utils.PluginFileUtils;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.http.Response;
import com.swms.plugin.core.configuration.DataSourceConfiguration;
import com.swms.plugin.core.model.dto.PluginDTO;
import com.swms.plugin.core.service.PluginService;
import com.swms.plugin.core.utils.SignatureUtil;
import com.swms.tenant.config.util.TenantContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("plugin")
@AllArgsConstructor
@Slf4j
@Validated
public class PluginController {

    private static final String SIGNATRUE = "HP2021!@#";

    private static final String JAR = ".jar";

    private static final String YML = ".yml";

    private static final String UTF8 = "UTF-8";

    private static final String PROPERTIES = ".properties";

    private static final String DIR_PLUGINS = "plugins";

    private final PluginService pluginService;

    @PostMapping
    public Response<String> add(@RequestParam(name = "jarFile") MultipartFile jarFile,
                                @RequestParam(name = "jarConfigFile", required = false) MultipartFile jarConfigFile,
                                @RequestParam(name = "param") PluginDTO pluginDTO) throws Exception {

        TenantContext.setCurrentTenant(DataSourceConfiguration.SWMS_PLUGIN_TENANT);
        String configFilePath = "";
        //上传插件配置文件
        if (jarConfigFile != null && jarConfigFile.getSize() > 0) {
            configFilePath = uploadFile(pluginDTO.getCode(), pluginDTO.getVersion(), jarConfigFile, 1);
        }

        String jarFilePath = uploadFile(pluginDTO.getCode(), pluginDTO.getVersion(), jarFile, 0);

        pluginDTO.setJarFilePath(jarFilePath);
        pluginDTO.setConfigFilePath(configFilePath);
        pluginService.add(pluginDTO);

        return Response.success();
    }


    @DeleteMapping("/{pluginId}")
    public Response<String> delete(@PathVariable("pluginId") Long pluginId) {
        TenantContext.setCurrentTenant(DataSourceConfiguration.SWMS_PLUGIN_TENANT);
        pluginService.delete(pluginId);
        return Response.success();
    }

    @GetMapping("download")
    public void download(@RequestParam(value = "code") String code,
                         @RequestParam(value = "version") String version,
                         @RequestParam(value = "type") Integer type,
                         HttpServletResponse response) {
        try {
            downloadFile(code, version, type, response);

        } catch (Exception e) {
            log.error("下载插件 code:'{}' version:'{}' type:'{}' 失败. {}",
                code, version, type, e.getMessage());
        }
    }


    /**
     * 上传插件或配置文件
     *
     * @param pluginId
     * @param version
     * @param file
     * @param type
     *
     * @return
     *
     * @throws Exception
     */
    private String uploadFile(String pluginId, String version, MultipartFile file, int type) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("Method:uploadPlugin param 'file' can not be null");
        }
        try {
            String fileName = file.getOriginalFilename();
            //插件强制命名如下形式：pluginId-1.0.0.jar
            if (type == 0) {
                String signatrue = SignatureUtil.doSign(pluginId + version, UTF8, SIGNATRUE, "MD5");
                fileName = pluginId + "(" + signatrue.substring(0, 5) + ")" + "-" + version + JAR;
            } else {
                if (StringUtils.equals(fileName, YML)) {
                    fileName = pluginId + YML;
                } else {
                    fileName = pluginId + PROPERTIES;
                }
            }
            String configPath = DIR_PLUGINS + File.separator + pluginId + File.separator + version + File.separator + fileName;
            File newFile = PluginFileUtils.createExistFile(Paths.get(configPath));
            // 然后写入数据到该文件
            Files.write(newFile.getAbsoluteFile().toPath(), file.getBytes());
            return newFile.getAbsolutePath();
        } catch (Exception ex) {
            log.error("上传出错。标识=" + pluginId + ",版本=" + version + ",类型=" + type, ex);
            throw ex;
        }
    }

    /**
     * 下载文件
     *
     * @param code
     * @param version
     * @param type
     * @param response
     *
     * @throws Exception
     */
    private void downloadFile(String code, String version, Integer type, HttpServletResponse response) throws Exception {
        String fileName;
        //插件强制命名如下形式：pluginId-1.0.0.jar
        if (type == 0) {
            String sign = SignatureUtil.doSign(code + version, UTF8, SIGNATRUE, "MD5");
            sign = sign.substring(0, 5).toLowerCase();
            fileName = code + "(" + sign + ")" + "-" + version + JAR;
        } else {
            fileName = code + YML;
        }
        String filePath = DIR_PLUGINS + File.separator + code + File.separator + version + File.separator + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new WmsException("file not exist");
        }
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buffer = new byte[1024];

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()) {
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            os.flush();
        }
    }

}

