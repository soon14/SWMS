package com.swms.user.api.dto.constants;

import com.swms.common.utils.dictionary.Dictionary;
import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sws
 */
@Getter
@AllArgsConstructor
public enum SystemCodeEnum implements IEnum {
    WMS("wms", "wms"),
    MDM("mdm", "mdm"),
    USER("user", "user"),
    PLUGIN("plugin", "plugin");

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

}
