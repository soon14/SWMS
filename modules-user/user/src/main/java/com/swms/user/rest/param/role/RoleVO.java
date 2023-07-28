package com.swms.user.rest.param.role;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@Data
public class RoleVO {
    /**
     * 角色id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 创建用户
     */
    @ApiModelProperty("createUser")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty("gmtCreated")
    private Long gmtCreated;

    /**
     * 修改用户
     */
    @ApiModelProperty("modifiedUser")
    private String modifiedUser;

    /**
     * 修改时间
     */
    @ApiModelProperty("gmtModified")
    private Long gmtModified;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String code;

    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty("帐号状态（1启用, 0停用）")
    private Integer status;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty(value = "仓库权限")
    @NotNull
    private List<String> warehouseCodes;

    public String getWarehouseCodes() {
        if (warehouseCodes == null) {
            return "";
        }
        return StringUtils.join(warehouseCodes, ",");
    }
}
