package com.swms.plugin.core.model.repository;

import com.swms.plugin.core.model.entity.TenantPluginConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantPluginConfigRepository extends JpaRepository<TenantPluginConfig, Long> {

    TenantPluginConfig findByTenantNameAndPluginCode(String tenantName, String pluginCode);

    List<TenantPluginConfig> findByPluginCode(String pluginCode);
}
