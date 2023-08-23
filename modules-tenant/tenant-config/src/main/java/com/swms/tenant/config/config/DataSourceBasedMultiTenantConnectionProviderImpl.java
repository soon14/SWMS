package com.swms.tenant.config.config;

import com.google.common.collect.Maps;
import com.swms.tenant.api.dto.TenantDTO;
import com.swms.tenant.config.exception.TenantException;
import com.swms.tenant.config.facade.TenantFacade;
import com.swms.tenant.config.util.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.Serial;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;


/**
 * This class does the job of selecting the correct database based on the tenant id found by the
 * {@link CurrentTenantIdentifierResolverImpl}
 */
@Configuration
@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl
    extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Injected MasterTenantRepository to access the tenant information from the master_tenant table
     */
    @Autowired
    private TenantFacade tenantApi;

    /**
     * Map to store the tenant ids as key and the data source as the value
     */
    private static Map<String, DataSource> dataSourcesMtApp = Maps.newConcurrentMap();

    @Override
    protected DataSource selectAnyDataSource() {
        // This method is called more than once. So check if the data source map
        // is empty. If it is then rescan master_tenant table for all tenant
        // entries.
        if (dataSourcesMtApp.isEmpty()) {
            List<TenantDTO> masterTenants = tenantApi.getAllTenants();
            if (masterTenants == null || masterTenants.isEmpty()) {
                return null;
            }
            log.info(">>>> selectAnyDataSource :{} -- Total tenants:", masterTenants.size());
            for (TenantDTO masterTenant : masterTenants) {
                dataSourcesMtApp.put(masterTenant.getTenantId(), DataSourceUtil.createAndConfigureDataSource(masterTenant));
            }
        }
        return dataSourcesMtApp.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        // If the requested tenant id is not present check for it in the master
        // database 'master_tenant' table
        if (!dataSourcesMtApp.containsKey(tenantIdentifier)) {
            TenantDTO tenantDTO = tenantApi.getTenant(tenantIdentifier);
            if (tenantDTO == null) {
                log.error("Trying to get tenant: {} which was not found in master db after rescan.", tenantIdentifier);
                throw new TenantException(String.format("Tenant not found after rescan, " + " tenant=%s", tenantIdentifier));
            }
            dataSourcesMtApp.put(tenantDTO.getTenantId(), DataSourceUtil.createAndConfigureDataSource(tenantDTO));
        }
        //check again if tenant exist in map after rescan master_db, if not, throw UsernameNotFoundException
        if (!dataSourcesMtApp.containsKey(tenantIdentifier)) {
            log.warn("Trying to get tenant: {} which was not found in master db after rescan.", tenantIdentifier);
            throw new TenantException(String.format("Tenant not found after rescan, " + " tenant=%s", tenantIdentifier));
        }
        return dataSourcesMtApp.get(tenantIdentifier);
    }

    public void addDataSource(String identifier, DataSource dataSource) {
        dataSourcesMtApp.put(identifier, dataSource);
    }
}
