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
public enum MenuTypeEnum implements IEnum {
    MENU("1", "系统"),
    PAGE("2", "菜单"),
    PERMISSION("3", "权限");

    private String code;
    private String desc;

    public static List<String> getValues() {
        List<String> result = Lists.newArrayList();
        for (MenuTypeEnum item : MenuTypeEnum.values()) {
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
        return MenuTypeEnum.class.getSimpleName();
    }
}
