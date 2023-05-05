package com.swms.user.rest.common.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author sws
 * @Date 2021/3/23 9:10
 * @Description:
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum SystemEnum implements IEnum {
    IWMS("iwms", "iwms"),
    ESS("ess", "ess"),
    AIAAS("aiaas", "aiaas"),
    LINK("hairoutech-link", "hairoutech-link"),
    I18N("hairoutech-i18n", "hairoutech-i18n"),
    USER("hairoutech-user", "hairoutech-user"),
    SIMU("hairoutech-simu", "hairoutech-simu"),
    DATA("hairoutech-data", "hairoutech-data");

    private String code;
    private String desc;

    public static List<String> getValues() {
        List<String> result = Lists.newArrayList();
        for (SystemEnum item : SystemEnum.values()) {
            result.add(item.name());
        }
        return result;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String getName() {
        return SystemEnum.class.getSimpleName();
    }
}
