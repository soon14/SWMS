package com.swms.plugin.core.model.repository;

import com.swms.plugin.core.model.entity.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PluginRepository extends JpaRepository<Plugin, Long> {

    List<Plugin> findByCode(String code);

    Plugin findByCodeAndVersion(String code, String version);
}
