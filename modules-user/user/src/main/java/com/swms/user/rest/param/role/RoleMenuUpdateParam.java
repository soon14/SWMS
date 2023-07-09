package com.swms.user.rest.param.role;


import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 添加角色权限参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("添加角色权限参数")
public class RoleMenuUpdateParam {
    /**
     * 选中菜单id
     */
    @ApiModelProperty(name = "menus", value = "选中菜单id")
    private Set<Long> menuSet;
    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", value = "角色id")
    private Long roleId;

    private String menus;

    public Set<Long> getMenuSet() {
        if (StringUtils.isEmpty(menus)) {
            return Collections.emptySet();
        }
        return Arrays.stream(menus.split(",")).map(Long::parseLong).collect(Collectors.toSet());
    }
}
