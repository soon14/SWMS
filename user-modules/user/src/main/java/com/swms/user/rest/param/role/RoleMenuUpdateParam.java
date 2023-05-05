package com.swms.user.rest.param.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

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
    private Set<Long> menus;
    /**
     * 未选中父菜单id
     */
    @ApiModelProperty(name = "parentMenus", value = "未选中父菜单id")
    private Set<Long> parentMenus;
    /**
     * 角色id
     */
    @ApiModelProperty(name = "roleId", value = "角色id", required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;
}
