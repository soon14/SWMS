package com.swms.user.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @author sws
 * @version 1.0
 * @since 2020-07-06
 */
@Data
public class AuthUserInfo {
    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty("最后登录时间")
    private String lastGmtLoginTime;

    @ApiModelProperty("角色列表")
    private Set<String> roles;

    @ApiModelProperty("权限列表")
    private Set<String> permissions;

    @ApiModelProperty("角色列表")
    private String roleNames;

}
