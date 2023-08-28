package com.swms.plugin.core.controller;

import com.google.common.collect.Maps;
import com.swms.common.utils.constants.RedisConstants;
import com.swms.common.utils.http.Response;
import com.swms.common.utils.user.AuthConstants;
import com.swms.distribute.file.client.FastdfsClient;
import com.swms.mq.MqClient;
import com.swms.plugin.api.constants.PluginManageTypeEnum;
import com.swms.plugin.api.constants.TenantPluginStatusEnum;
import com.swms.plugin.api.dto.PluginManageDTO;
import com.swms.plugin.core.model.dto.PluginDTO;
import com.swms.plugin.core.model.entity.Plugin;
import com.swms.plugin.core.model.entity.TenantInstallPlugin;
import com.swms.plugin.core.model.repository.PluginRepository;
import com.swms.plugin.core.model.repository.TenantInstallPluginRepository;
import com.swms.plugin.core.service.PluginManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pluginManage")
@Slf4j
@Validated
public class PluginManageController {

    @Autowired
    private PluginManagementService pluginService;

    @Autowired
    private PluginRepository pluginRepository;

    @Autowired
    private TenantInstallPluginRepository tenantInstallPluginRepository;

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
    public Response<String> addPlugin(@Valid PluginDTO pluginDTO,
                                      @RequestParam(value = "jarFile") MultipartFile jarFile,
                                      @RequestParam(value = "configFile", required = false) MultipartFile configFile) throws IOException {
        String jarFilePath = fastdfsClient.updateFile(jarFile.getInputStream(), jarFile.getSize(), jarFile.getName(), null);
        pluginDTO.setJarFilePath(jarFilePath);

        if (configFile != null) {
            String configFilePath = fastdfsClient.updateFile(configFile.getInputStream(), configFile.getSize(), configFile.getName(), null);
            pluginDTO.setConfigFilePath(configFilePath);
        }

        pluginService.addPlugin(pluginDTO);
        return Response.success();
    }

    @DeleteMapping("/{pluginId}")
    public Response<String> delete(@PathVariable("pluginId") Long pluginId) {
        pluginService.delete(pluginId);
        return Response.success();
    }

    @GetMapping(value = "/install/{id}")
    public Response install(@PathVariable Long id, HttpServletRequest request) {

        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        //save tenant install plugin record
        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantInstallPlugin tenantInstallPlugin = TenantInstallPlugin.builder().tenantName(tenantName)
            .pluginCode(plugin.getCode())
            .pluginId(plugin.getId())
            .status(TenantPluginStatusEnum.INSTALLED)
            .build();
        tenantInstallPluginRepository.save(tenantInstallPlugin);

        operate(plugin, PluginManageTypeEnum.INSTALL);
        return Response.success();
    }

    @GetMapping(value = "/start/{id}")
    public Response start(@PathVariable Long id) {
        operate(id, PluginManageTypeEnum.START);
        return Response.success();
    }

    @GetMapping(value = "/stop/{id}")
    public Response stop(@PathVariable Long id) {
        operate(id, PluginManageTypeEnum.STOP);
        return Response.success();
    }

    @GetMapping(value = "/restart/{id}")
    public Response restart(@PathVariable Long id) {
        operate(id, PluginManageTypeEnum.RESTART);
        return Response.success();
    }


    @GetMapping(value = "/uninstall/{id}")
    public Response uninstall(@PathVariable Long id, HttpServletRequest request) {

        Plugin plugin = pluginRepository.findById(id).orElseThrow();

        String tenantName = request.getHeader(AuthConstants.TENANT_ID_HEADER);
        TenantInstallPlugin tenantInstallPlugin = tenantInstallPluginRepository.findByTenantNameAndPluginId(tenantName, plugin.getId());
        tenantInstallPluginRepository.delete(tenantInstallPlugin);

//        operate(plugin, PluginManageTypeEnum.UNINSTALL);
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
            .pluginConfigPath(plugin.getConfigFilePath())
            .version(plugin.getPluginVersion())
            .build();
        mqClient.sendMessage(RedisConstants.PLUGIN_LISTEN_PLUGIN_MANAGEMENT, pluginManageDTO);
    }

}
