package com.swms.gateway.constant;

public interface SystemConstant {
    /**
     * 返回前端格式
     */
    String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    /**
     * SUPPER_PERMISSION
     */
    String SUPPER_PERMISSION = "*:*";
    /**
     * HEADER_AUTHORIZATION
     */
    String HEADER_AUTHORIZATION = "Authorization";

    String HEADER_WAREHOUSE = "X-WarehouseID";

    String HEADER_TENANT_ID = "X-TenantID";
}
