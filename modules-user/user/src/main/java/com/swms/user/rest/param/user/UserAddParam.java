package com.swms.user.rest.param.user;

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
@ApiModel("添加用户参数")
public class UserAddParam {


    /**
     * 所属角色id集合
     */
    @ApiModelProperty(name = "roleIds", value = "角色id集合", required = true)
    private String roleIds;

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "name", value = "用户名称", required = true)
    @NotEmpty(message = "姓名不能为空")
    @Size(max = 32, message = "姓名不能超过32位")
    private String name;

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "username", value = "用户名", required = true)
    @NotEmpty
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", required = true)
    private String password = "123456";

    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty(name = "status", value = "帐号状态（1启用, 0停用，参考枚举YesOrNo）", required = true)
    @NotNull
    private Integer status = 1;

    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone", value = "手机号", required = true)
    @NotEmpty
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;

}
