package com.swms.user.rest.param.user;

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
@ApiModel("查询用户所属角色参数")
public class UserRoleFetchParam {
    @ApiModelProperty(name = "userId", value = "用户Id")
    @NotNull(message = "用户Id不能为空")
    private Long userId;
}
