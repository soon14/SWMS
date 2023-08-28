package com.swms.user.api.dto.constants;

import com.swms.common.utils.dictionary.Dictionary;
import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sws
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum implements IEnum {
    NORMAL("NORMAL", "普通账号"),
    ANTA("ANTA", "安踏外部账号"),
    GITEE("GITEE", "GITEE");

    private String code;
    private String desc;

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.desc;
    }

    public static UserTypeEnum getByCode(String code) {
        for (UserTypeEnum value : UserTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(code, value.getValue())) {
                return value;
            }
        }
        return null;
    }
}
