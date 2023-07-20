package com.swms.utils.dictionary;

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
    String getValue();

    /**
     * 获得枚举的Desc
     *
     * @return 枚举Desc
     */
    String getLabel();

}
