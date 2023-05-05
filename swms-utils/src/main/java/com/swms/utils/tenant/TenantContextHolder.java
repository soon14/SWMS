package com.swms.utils.tenant;

public class TenantContextHolder {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static String getTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }
}
