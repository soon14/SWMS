package com.swms.user.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.swms.user.api.TenantApi;
import com.swms.user.api.dto.TenantDTO;
import com.swms.user.tenant.model.Tenant;
import com.swms.user.tenant.repository.TenantRepository;
import com.swms.user.tenant.service.TenantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class TenantServiceImpl implements TenantService, TenantApi {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public TenantDTO getTenant(String tenantId) {
        Wrapper<Tenant> wrapper = Wrappers.<Tenant>lambdaQuery().eq(Tenant::getTenantId, tenantId);
        Tenant tenant = tenantRepository.selectOne(wrapper);
        if (tenant != null) {
            TenantDTO tenantDTO = new TenantDTO();
            tenantDTO.setUrl(tenant.getUrl());
            tenantDTO.setTenantId(tenant.getTenantId());
            tenantDTO.setUsername(tenant.getUsername());
            tenantDTO.setPassword(tenant.getPassword());
            tenantDTO.setDriverClassName(tenant.getDriverClassName());
            return tenantDTO;
        }
        return null;
    }

    @Override
    public List<TenantDTO> findAll() {

        List<Tenant> tenants = tenantRepository.selectList(null);
        if (tenants != null && tenants.size() > 0) {

            return tenants.stream().map(v -> {
                TenantDTO tenantDTO = new TenantDTO();
                BeanUtils.copyProperties(v, tenantDTO);
                return tenantDTO;
            }).toList();
        }

        return null;
    }
}
