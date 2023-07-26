package com.swms.wms;

import com.swms.tenant.api.ITenantApi;
import com.swms.tenant.config.facade.TenantFacade;
import com.swms.tenant.config.util.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WmsApplicationTest.class)
public class BaseTest {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void initBean() {
        TenantFacade tenantFacade = applicationContext.getBean(TenantFacade.class);
        ITenantApi iTenantApi = applicationContext.getBean("mockITenantApi", ITenantApi.class);
        tenantFacade.setTenantApi(iTenantApi);

        //set default tenant
        TenantContext.setCurrentTenant("test");
    }
}
