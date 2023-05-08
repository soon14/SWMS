package com.swms.user.rest.param.login;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @ApiModelProperty(name = "username", value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    @Size(max = 32, message = "用户名不能超过32位")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能小于6位")
    private String password;
}
