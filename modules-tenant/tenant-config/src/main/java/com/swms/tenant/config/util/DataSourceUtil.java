package com.swms.tenant.config.util;

import com.swms.tenant.api.dto.TenantDTO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DataSourceUtil {

    private DataSourceUtil() {
        throw new IllegalStateException("Utility class");
    }

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
}
