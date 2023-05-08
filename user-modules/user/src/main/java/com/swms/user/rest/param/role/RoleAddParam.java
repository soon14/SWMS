package com.swms.user.rest.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 添加角色参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("添加角色参数")
public class RoleAddParam {

    /**
     * 角色名称
     */
    @ApiModelProperty(name = "name", value = "角色名称", required = true)
    @NotEmpty(message = "角色名称不能为空")
    @Size(max = 16, message = "角色名称不能超过16位")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty(name = "code", value = "角色编码", required = true)
    @NotEmpty(message = "角色编码不能为空")
    @Size(max = 16, message = "角色编码不能超过16位")
    private String code;

    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty(name = "status", value = "帐号状态（1启用, 0停用，参考枚举YesOrNo）", required = true)
    @NotNull(message = "帐号状态不能为空")
    private Integer status;

    /**
     * 角色描述
     */
    @ApiModelProperty(name = "description", value = "角色描述")
    @Size(max = 32, message = "描述不能超过32位")
    private String description;
}
