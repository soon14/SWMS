/*
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.swms.tenant.config.config;

import com.google.common.collect.Maps;
import com.swms.tenant.api.dto.TenantDTO;
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
 *
 * @author Sunit Katkar, sunitkatkar@gmail.com (https://sunitkatkar.blogspot.com/)
 * @version 1.0
 * @since ver 1.0 (May 2018)
 */
@Configuration
@Slf4j
@SuppressWarnings("unchecked")
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
                throw new RuntimeException(String.format("Tenant not found after rescan, " + " tenant=%s", tenantIdentifier));
            }
            dataSourcesMtApp.put(tenantDTO.getTenantId(), DataSourceUtil.createAndConfigureDataSource(tenantDTO));
        }
        //check again if tenant exist in map after rescan master_db, if not, throw UsernameNotFoundException
        if (!dataSourcesMtApp.containsKey(tenantIdentifier)) {
            log.warn("Trying to get tenant: {} which was not found in master db after rescan.", tenantIdentifier);
            throw new RuntimeException(String.format("Tenant not found after rescan, " + " tenant=%s", tenantIdentifier));
        }
        return dataSourcesMtApp.get(tenantIdentifier);
    }

}
