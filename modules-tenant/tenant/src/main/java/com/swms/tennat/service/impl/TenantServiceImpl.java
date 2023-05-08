package com.swms.tennat.service.impl;

import com.swms.tenant.api.ITenantApi;
import com.swms.tenant.api.dto.TenantDTO;
import com.swms.tennat.model.entity.Tenant;
import com.swms.tennat.model.repository.TenantRepository;
import com.swms.tennat.service.TenantService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@DubboService
@Validated
public class TenantServiceImpl implements ITenantApi {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public TenantDTO getTenant(String tenantId) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId);
        if (tenant == null) {
            return null;
        }
        TenantDTO tenantDTO = new TenantDTO();
        BeanUtils.copyProperties(tenant, tenantDTO);
        return tenantDTO;
    }

    @Override
    public List<TenantDTO> findAll() {
        List<Tenant> tenants = tenantRepository.findAll();
        if (!tenants.isEmpty()) {
            return tenants.stream().map(v -> {
                TenantDTO tenantDTO = new TenantDTO();
                BeanUtils.copyProperties(v, tenantDTO);
                return tenantDTO;
            }).toList();
        }
        return null;
    }
}
