package com.swms.mdm.api.config.constants.copy;

import com.swms.common.utils.dictionary.Dictionary;
import com.swms.common.utils.dictionary.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sws
 * @Date 2021/3/23 9:10
 * @Description:
 */
@Getter
@AllArgsConstructor
@Dictionary
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
