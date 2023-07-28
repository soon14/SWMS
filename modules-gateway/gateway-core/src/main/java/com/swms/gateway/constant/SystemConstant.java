package com.swms.gateway.constant;

public class SystemConstant {

    private SystemConstant() {

    }

    /**
     * 返回前端格式
     */
    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    /**
     * SUPPER_PERMISSION
     */
    public static final String SUPPER_PERMISSION = "*:*";
    /**
     * HEADER_AUTHORIZATION
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HEADER_WAREHOUSE = "X-WarehouseID";

    public static final String HEADER_TENANT_ID = "X-TenantID";
}
