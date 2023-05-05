package com.swms.user.api;

import com.swms.user.api.dto.TenantDTO;

import java.util.List;

public interface TenantApi {

    TenantDTO getTenant(String tenantId);

    List<TenantDTO> findAll();
}
