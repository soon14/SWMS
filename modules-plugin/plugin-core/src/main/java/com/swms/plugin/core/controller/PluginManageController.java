package com.swms.plugin.core.controller;

import com.google.common.collect.Maps;
import com.swms.common.utils.constants.RedisConstants;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.http.Response;
import com.swms.common.utils.user.AuthConstants;
import com.swms.distribute.file.client.FastdfsClient;
import com.swms.mq.MqClient;
import com.swms.plugin.api.constants.PluginManageTypeEnum;
import com.swms.plugin.api.constants.TenantPluginStatusEnum;
import com.swms.plugin.api.dto.PluginManageDTO;
import com.swms.plugin.api.dto.PluginDTO;
import com.swms.plugin.api.dto.TenantPluginConfigDTO;
import com.swms.plugin.core.model.entity.Plugin;
import com.swms.plugin.core.model.entity.TenantPlugin;
import com.swms.plugin.core.model.entity.TenantPluginConfig;
import com.swms.plugin.core.model.repository.PluginRepository;
import com.swms.plugin.core.model.repository.TenantPluginRepository;
import com.swms.plugin.core.service.PluginManagementService;
import com.swms.plugin.core.service.TenantPluginConfigService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RestController
@RequestMapping("/pluginManage")
@Slf4j
@Validated
public class PluginManageController {

    @Autowired
    private PluginManagementService pluginService;

    @Autowired
    private TenantPluginConfigService tenantPluginConfigService;

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private TenantPluginRepository tenantInstallPluginRepository;

    @Autowired
    private MqClient mqClient;

    @Autowired
    private FastdfsClient fastdfsClient;

    @GetMapping(value = "/listAll")
    public Response<List<Plugin>> listAll() {
        return Response.success(pluginRepository.findAll());
    }

    @GetMapping(value = "/storeQuery")
    public Response<Object> storeQuery() {
        HashMap<@Nullable String, @Nullable Object> result = Maps.newHashMap();
        result.put("pluginStore", pluginRepository.findAll());
        return Response.success(result);
    }

    @PostMapping("uploadFile")
    public Response<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return Response.success(fastdfsClient.updateFile(file.getInputStream(), file.getSize(), file.getName(), null));
    }

    @PostMapping("addPlugin")
    public Response<String> addPlugin(PluginDTO pluginDTO,
                                      @RequestParam(value = "jarFile") MultipartFile multipartFile) throws IOException {

        String jarFilePath = fastdfsClient.updateFile(multipartFile.getInputStream(), multipartFile.getSize(), multipartFile.getName(), null);

        parseJarFile(multipartFile, pluginDTO);

        pluginDTO.setJarFilePath(jarFilePath);

        pluginService.addPlugin(pluginDTO);
        return Response.success();
    }

    @DeleteMapping("/{pluginId}")
    public Response<String> delete(@PathVariable("pluginId") Long pluginId) {
        pluginService.delete(pluginId);
        return Response.success();
    }

    @GetMapping(value = "/install/{id}")
    public Response<Object> install(@PathVariable Long id, HttpServletRequest request) {

        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        //save tenant install plugin record
        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantPlugin tenantInstallPlugin = TenantPlugin.builder().tenantName(tenantName)
            .pluginCode(plugin.getCode())
            .pluginId(plugin.getId())
            .status(TenantPluginStatusEnum.STARTED)
            .build();
        tenantInstallPluginRepository.save(tenantInstallPlugin);

        operate(plugin, PluginManageTypeEnum.INSTALL);
        return Response.success();
    }

    @GetMapping(value = "/start/{id}")
    public Response<Object> start(@PathVariable Long id, HttpServletRequest request) {
        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantPlugin tenantInstallPlugin = tenantInstallPluginRepository.findByTenantNameAndPluginId(tenantName, plugin.getId());
        tenantInstallPlugin.setStatus(TenantPluginStatusEnum.STARTED);

        operate(id, PluginManageTypeEnum.START);
        return Response.success();
    }

    @GetMapping(value = "/stop/{id}")
    public Response<Object> stop(@PathVariable Long id, HttpServletRequest request) {
        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantPlugin tenantInstallPlugin = tenantInstallPluginRepository.findByTenantNameAndPluginId(tenantName, plugin.getId());
        tenantInstallPlugin.setStatus(TenantPluginStatusEnum.STOPPED);

        tenantInstallPluginRepository.delete(tenantInstallPlugin);

        return Response.success();
    }

    @GetMapping(value = "/restart/{id}")
    public Response<Object> restart(@PathVariable Long id, HttpServletRequest request) {
        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantPlugin tenantInstallPlugin = tenantInstallPluginRepository.findByTenantNameAndPluginId(tenantName, plugin.getId());
        tenantInstallPlugin.setStatus(TenantPluginStatusEnum.STARTED);
        tenantInstallPluginRepository.save(tenantInstallPlugin);

        return Response.success();
    }

    @GetMapping(value = "/uninstall/{id}")
    public Response<Object> uninstall(@PathVariable Long id, HttpServletRequest request) {

        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantPlugin tenantInstallPlugin = tenantInstallPluginRepository.findByTenantNameAndPluginId(tenantName, plugin.getId());
        tenantInstallPluginRepository.delete(tenantInstallPlugin);

        return Response.success();
    }

    @GetMapping(value = "/config/{pluginCode}")
    public Response<Object> tenantConfig(@PathVariable String pluginCode, HttpServletRequest request) {

        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantPluginConfig tenantPluginConfig = tenantPluginConfigService.getTenantConfig(tenantName, pluginCode);
        return Response.success(tenantPluginConfig);
    }

    @PostMapping(value = "/config/{id}")
    public Response<Object> config(@PathVariable Long id, @RequestBody TenantPluginConfigDTO tenantPluginConfigDTO,
                                   HttpServletRequest request) {

        if (tenantPluginConfigDTO.getId() != null) {
            tenantPluginConfigService.updateConfig(tenantPluginConfigDTO);
        } else {
            String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
            tenantPluginConfigDTO.setTenantName(tenantName);
            Plugin plugin = pluginRepository.findById(id).orElseThrow();
            tenantPluginConfigDTO.setPluginCode(plugin.getCode());
            tenantPluginConfigService.saveConfig(tenantPluginConfigDTO);
        }

        return Response.success();
    }

    private void operate(Long id, PluginManageTypeEnum pluginManageType) {
        Plugin plugin = pluginRepository.findById(id).orElseThrow();
        operate(plugin, pluginManageType);
    }

    private void operate(Plugin plugin, PluginManageTypeEnum pluginManageType) {
        PluginManageDTO pluginManageDTO = PluginManageDTO.builder().pluginId(plugin.getCode())
            .pluginManageType(pluginManageType)
            .pluginJarPath(plugin.getJarFilePath())
            .version(plugin.getPluginVersion())
            .build();
        mqClient.sendMessage(RedisConstants.PLUGIN_LISTEN_PLUGIN_MANAGEMENT, pluginManageDTO);
    }


    private void parseJarFile(MultipartFile multipartFile, PluginDTO pluginDTO) throws IOException {
        File tempFile = File.createTempFile("temp", ".jar");
        multipartFile.transferTo(tempFile);
        try (JarFile jarFile = new JarFile(tempFile)) {
            JarEntry jarEntry = jarFile.getJarEntry("plugin.properties");
            if (jarEntry == null) {
                throw new WmsException("plugin.properties is not exits.");
            }

            List<String> lines = IOUtils.readLines(jarFile.getInputStream(jarEntry));
            for (String line : lines) {
                String[] splits = line.split("=");
                if (StringUtils.isEmpty(line) || splits.length < 2) {
                    continue;
                }
                String key = splits[0];
                String value = splits[1];
                if (Objects.equals(key, "plugin.id")) {
                    pluginDTO.setCode(value);
                } else if (Objects.equals(key, "plugin.name")) {
                    pluginDTO.setName(value);
                } else if (Objects.equals(key, "plugin.provider")) {
                    pluginDTO.setDeveloper(value);
                } else if (Objects.equals(key, "plugin.version")) {
                    pluginDTO.setPluginVersion(value);
                } else if (Objects.equals(key, "plugin.description")) {
                    pluginDTO.setDescription(value);
                } else if (Objects.equals(key, "plugin.dependencies")) {
                    pluginDTO.setDependencies(value);
                }
            }

        } catch (IOException e) {
            log.error("parse jar file error:", e);
            throw e;
        } finally {
            Files.delete(Path.of(tempFile.getPath()));
        }
    }
}
