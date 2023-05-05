package com.swms.user.rest.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author sws
 * @Date 2021/3/24 16:36
 * @Description:
 */
@Data
@ApiModel("查询当前角色的菜单和权限参数")
public class RoleMenuFetchParam {
    @ApiModelProperty(name = "roleId", value = "角色Id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;
}
