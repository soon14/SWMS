package com.swms.tenant.config.util;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TenantContext {

    private TenantContext() {

    }

    private static final TransmittableThreadLocal<String> CURRENT_TENANT = new TransmittableThreadLocal<>();

    public static String getTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }
}
