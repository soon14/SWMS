package com.swms.user.rest.param.role;

import com.swms.user.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页查询参数
 *
 * @author sws
 * @version 1.0
 * @since 2020-12-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色分页查询参数")
public class RolePageParam extends PageParam {

    @ApiModelProperty(name = "name", value = "角色名称", required = false)
    private String name;


    @ApiModelProperty(name = "roleCode", value = "角色编号", required = false)
    private String code;

    @ApiModelProperty(name = "status", value = "状态(1: 启用、0: 禁用，参考枚举YesOrNo)", required = false)
    private Integer status;
}
