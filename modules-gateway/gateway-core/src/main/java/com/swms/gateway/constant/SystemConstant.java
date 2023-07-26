package com.swms.gateway.constant;

public interface SystemConstant {
    /**
     * 登出提示
     */
    String LOGOUT_MSG = "退出成功";
    /**
     * 登出url
     */
    String LOGOUT_URL = "/user/external/logout";
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
    /**
     * JWT_AUTHORITIES
     */
    String JWT_AUTHORITIES = "authorities";

    String JWT_SECRET = "linsan";
}
