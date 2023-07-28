package com.swms.user.rest.param.role;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 修改角色参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("修改角色参数")
public class RoleUpdateParam {

    /**
     * 角色名称
     */
    @ApiModelProperty(name = "id", value = "角色id", required = true)
    @NotNull(message = "角色id不能为空")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(name = "name", value = "角色名称", required = true)
    @NotEmpty(message = "角色名称不能为空")
    @Size(max = 16, message = "角色名称不能超过16位")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty(name = "code", value = "角色编码", required = true)
    @NotEmpty(message = "角色编码不能为空")
    @Size(max = 16, message = "角色编码不能超过16位")
    private String code;

    /**
     * 角色描述
     */
    @ApiModelProperty(name = "description", value = "角色描述")
    @Size(max = 32, message = "描述不能超过32位")
    private String description;

    @ApiModelProperty(name = "status", value = "是否启用（1-是、0-否，参考枚举YesOrNo）", required = true)
    @NotNull(message = "是否启用不能为空")
    private Integer status;

    @ApiModelProperty(value = "仓库权限")
    @NotNull
    private String warehouseCodes;

    public List<String> getWarehouseCodes() {
        return Lists.newArrayList(warehouseCodes.split(","));
    }
}
