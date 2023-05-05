package com.swms.user.rest.common.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author sws
 * @date 2021/7/22 9:31
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum UserTypeEnum implements IEnum {
    NORMAL("NORMAL", "普通账号"),
    ANTA("ANTA", "安踏外部账号"),
    GITEE("GITEE", "GITEE");

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

    public static UserTypeEnum getByCode(String code) {
        for (UserTypeEnum value : UserTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}
