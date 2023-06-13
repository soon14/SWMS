package com.swms.search.configuration;

import com.swms.tenant.config.util.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getTenant();
    }

}
