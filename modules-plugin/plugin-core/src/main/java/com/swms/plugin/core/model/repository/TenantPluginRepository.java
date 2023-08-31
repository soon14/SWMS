package com.swms.plugin.core.model.repository;

import com.swms.plugin.core.model.entity.TenantPlugin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantPluginRepository extends JpaRepository<TenantPlugin, Long> {

    TenantPlugin findByTenantNameAndPluginId(String tenantName, Long pluginId);

    List<TenantPlugin> findByTenantName(String tenantName);
}
