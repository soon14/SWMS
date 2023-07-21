package com.swms.user.rest.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 修改角色参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("修改用户参数")
public class UserUpdateParam {

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "id", value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long id;


    /**
     * 所属角色id集合
     */
    @ApiModelProperty(name = "roleIds", value = "角色id集合", required = true)
    @NotNull(message = "角色id集合不能为空")
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
    @NotEmpty(message = "用户名不能为空")
    @Size(max = 32, message = "用户名不能超过32位")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码")
    private String password;


    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty(name = "status", value = "帐号状态（1启用, 0停用，参考枚举YesOrNo）", required = true)
    @NotNull(message = "帐号状态不能为空")
    private Integer status;

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

    public Set<Long> getRoleIds() {
        if (StringUtils.isEmpty(this.roleIds)) {
            return Collections.emptySet();
        }
        return Arrays.stream(this.roleIds.split(",")).map(Long::parseLong).collect(Collectors.toSet());
    }
}
