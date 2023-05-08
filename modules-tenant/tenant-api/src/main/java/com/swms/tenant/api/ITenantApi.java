package com.swms.tenant.api;

import com.swms.tenant.api.dto.TenantDTO;

import java.util.List;

public interface ITenantApi {

    TenantDTO getTenant(String tenantId);

    List<TenantDTO> findAll();
}
