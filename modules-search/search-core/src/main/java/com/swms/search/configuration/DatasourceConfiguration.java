package com.swms.search.configuration;

import com.google.common.collect.Maps;
import com.swms.tenant.config.util.DataSourceUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    public DataSource dynamicDatasource(DataSourceUtil dataSourceUtil) {
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        dynamicDatasource.setTargetDataSources(Maps.newHashMap(dataSourceUtil.getAllDataSources()));
        return dynamicDatasource;
    }

}
