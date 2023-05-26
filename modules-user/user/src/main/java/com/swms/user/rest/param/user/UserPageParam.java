package com.swms.user.rest.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页查询参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("用户分页查询参数")
public class UserPageParam {

    @ApiModelProperty(name = "name", value = "姓名")
    private String name;

    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @ApiModelProperty(name = "status", value = "状态(1: 启用、0: 禁用 参考枚举YesOrNo)")
    private Integer status;

    @ApiModelProperty(name = "locked", value = "是否被锁(true: 账户锁定、false: 账户未锁定)")
    private Boolean locked;

}
