package com.swms.tennat.model.repository;

import com.swms.tennat.model.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByTenantId(String tenantId);
}
