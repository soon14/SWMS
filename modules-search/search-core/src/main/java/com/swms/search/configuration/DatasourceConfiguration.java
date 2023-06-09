package com.swms.search.configuration;

import com.google.common.collect.Maps;
import com.swms.tenant.config.util.DataSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Autowired
    private DataSourceUtil dataSourceUtil;

    @Bean
    public DataSource dynamicDatasource() {
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        dynamicDatasource.setTargetDataSources(Maps.newHashMap(dataSourceUtil.getAllDataSources()));
        return dynamicDatasource;
    }

}
