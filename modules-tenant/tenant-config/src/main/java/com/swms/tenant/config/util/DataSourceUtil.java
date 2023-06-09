package com.swms.tenant.config.util;

import com.swms.tenant.api.dto.TenantDTO;
import com.swms.tenant.config.facade.TenantFacade;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;

@Component
public class DataSourceUtil {

    @Autowired
    private TenantFacade tenantFacade;

    public static DataSource createAndConfigureDataSource(TenantDTO tenantDTO) {

        DataSourceBuilder<?> factory = DataSourceBuilder.create().url(tenantDTO.getUrl())
            .username(tenantDTO.getUsername()).password(tenantDTO.getPassword())
            .driverClassName(tenantDTO.getDriverClassName());
        HikariDataSource ds = (HikariDataSource) factory.build();
        ds.setKeepaliveTime(40000);
        ds.setMinimumIdle(10);
        ds.setMaxLifetime(45000);
        ds.setIdleTimeout(35000);
        return ds;
    }

    public Map<String, DataSource> getAllDataSources() {
        List<TenantDTO> allTenants = tenantFacade.getAllTenants();
        return allTenants.stream().collect(Collectors.toMap(TenantDTO::getTenantId, DataSourceUtil::createAndConfigureDataSource));
    }
}
