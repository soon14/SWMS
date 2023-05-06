package com.swms.user.rest.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 重置密码参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("重置用户密码参数")
public class UserResetPasswordParam {


    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "userId 不能为空")
    private Long userId;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能小于6位")
    private String password;
}
