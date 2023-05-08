package com.swms.tenant.config.facade;

import com.swms.tenant.api.ITenantApi;
import com.swms.tenant.api.dto.TenantDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantFacade {

    @DubboReference(check = false)
    private ITenantApi tenantApi;

    public void setTenantApi(ITenantApi tenantApi) {
        this.tenantApi = tenantApi;
    }

    public List<TenantDTO> getAllTenants() {
        return tenantApi.findAll();
    }

    public TenantDTO getTenant(String tenantId) {
        return tenantApi.getTenant(tenantId);
    }

}
