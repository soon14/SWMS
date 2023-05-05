package com.swms.user.rest.common;


/**
 * 枚举接口类
 *
 * @author sws
 * @version 1.0
 */
public interface ResponseEnum {

    int SUCCESS_CODE = 1;
    int ERROR_CODE = 0;

    /**
     * 得到code
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 得到信息
     *
     * @return String
     */
    String getMessage();

}
