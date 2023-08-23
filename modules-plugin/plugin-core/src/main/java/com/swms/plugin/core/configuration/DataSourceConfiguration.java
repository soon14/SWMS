package com.swms.plugin.core.configuration;

import com.swms.tenant.config.config.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    public static final String SWMS_PLUGIN_TENANT = "swms-plugin";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceBasedMultiTenantConnectionProviderImpl provider;

    @Bean
    public void addDatasource() {
        provider.addDataSource(SWMS_PLUGIN_TENANT, dataSource);
    }
}
