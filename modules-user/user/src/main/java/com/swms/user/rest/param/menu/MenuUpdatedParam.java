package com.swms.user.rest.param.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * 修改菜单的参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("修改菜单参数")
public class MenuUpdatedParam extends MenuAddParam {
    /**
     * 菜单id
     */
    @ApiModelProperty(name = "menuId", value = "菜单id", required = true)
    @NotNull(message = "菜单id不能为空")
    private Long id;
}
