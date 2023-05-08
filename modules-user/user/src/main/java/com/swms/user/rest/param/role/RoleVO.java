package com.swms.user.rest.param.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Data
public class RoleVO {
    /**
     * 角色id
     */
    @ApiModelProperty("id")
    private Long role_id;

    /**
     * 创建用户
     */
    @ApiModelProperty("createUser")
    private String role_createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty("gmtCreated")
    private Long role_gmtCreated;

    /**
     * 修改用户
     */
    @ApiModelProperty("modifiedUser")
    private String role_modifiedUser;

    /**
     * 修改时间
     */
    @ApiModelProperty("gmtModified")
    private Long role_gmtModified;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String role_name;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String role_code;

    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty("帐号状态（1启用, 0停用）")
    private Integer role_status;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String role_description;

}
