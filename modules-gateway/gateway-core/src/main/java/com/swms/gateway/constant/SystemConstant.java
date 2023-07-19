package com.swms.gateway.constant;

public interface SystemConstant {
    /**
     * 前端传入的国际化语言标识 请求头key
     */
    String LANG = "lang";
    /**
     * 浏览器传入的国际化语言标识 请求头key
     */
    String ACCEPT_LANGUAGE = "Accept-Language";
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
     * 用户名称
     */
    String USER_NAME = "username";
    /**
     * SUPPER_PERMISSION
     */
    String SUPPER_PERMISSION = "*:*";
    /**
     * HEADER_AUTHORIZATION
     */
    String HEADER_AUTHORIZATION = "Authorization";
    /**
     * TOKEN_BEARER
     */
    String TOKEN_BEARER = "Bearer";
    /**
     * JWT_AUTHORITIES
     */
    String JWT_AUTHORITIES = "authorities";

    String JWT_SECRET = "linsan";
}
