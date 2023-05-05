package com.swms.user.rest.common.enums;

/**
 * @author sws
 * @Date 2020/12/9 18:00
 * @Description: 基础的枚举接口
 */
public interface IEnum {

    /**
     * 获得枚举的Code
     *
     * @return 枚举Code
     */
    String getCode();

    /**
     * 获得枚举的Desc
     *
     * @return 枚举Desc
     */
    String getDesc();


    /**
     * 获取枚举的类名
     *
     * @return ""
     */
    default String getName() {
        return "";
    }
}
