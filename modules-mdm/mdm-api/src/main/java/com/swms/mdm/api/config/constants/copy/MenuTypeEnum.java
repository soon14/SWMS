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
public enum MenuTypeEnum implements IEnum {
    MENU("1", "系统"),
    PAGE("2", "菜单"),
    PERMISSION("3", "权限");

    private String value;
    private String label;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

}
