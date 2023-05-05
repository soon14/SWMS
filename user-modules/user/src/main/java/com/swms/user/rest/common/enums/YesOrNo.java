package com.swms.user.rest.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sws
 * @Date 2020/12/9 18:00
 * @Description: 是否类型枚举
 */
@SuppressWarnings("ALL")
@Getter
@AllArgsConstructor
public enum YesOrNo implements IEnum {
    YES("1", "是"),
    NO("0", "否");

    private String code;
    private String desc;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getName() {
        return YesOrNo.class.getSimpleName();
    }
}
