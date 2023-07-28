package com.swms.user.rest.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 修改角色参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("修改用户状态参数")
public class UserUpdateStatusParam {

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "userId", value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;


    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty(name = "status", value = "帐号状态（1启用, 0停用，参考枚举YesOrNo）", required = true)
    @NotNull(message = "帐号状态不能为空")
    private Integer status;
}
