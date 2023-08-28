package com.swms.plugin.core.model.repository;

import com.swms.plugin.core.model.entity.TenantInstallPlugin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantInstallPluginRepository extends JpaRepository<TenantInstallPlugin, Long> {

    TenantInstallPlugin findByTenantNameAndPluginId(String tenantName, Long pluginId);

    List<TenantInstallPlugin> findByTenantName(String tenantName);
}
