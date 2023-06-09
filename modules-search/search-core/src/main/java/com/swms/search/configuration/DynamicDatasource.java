package com.swms.search.configuration;

import com.swms.tenant.config.util.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 可以在拦截器中使用 ThreadLocal 记录当前租户信息
        // 然后在这里从 ThreadLocal 中取出
        return TenantContext.getTenant();      // 返回当前租户编号
    }

}
