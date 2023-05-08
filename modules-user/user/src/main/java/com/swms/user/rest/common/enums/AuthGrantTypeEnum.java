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
public enum AuthGrantTypeEnum implements IEnum {
    PASSWORD("password", "密码"),
    CLIENT_CREDENTIALS("client_credentials", "客户端"),
    REFRESH_TOKEN("refresh_token", "刷新token"),
    AUTHORIZATION_CODE("authorization_code", "授权码");

    private String code;
    private String desc;

    public static List<String> getValues() {
        List<String> result = Lists.newArrayList();
        for (AuthGrantTypeEnum item : AuthGrantTypeEnum.values()) {
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
        return AuthGrantTypeEnum.class.getSimpleName();
    }
}
