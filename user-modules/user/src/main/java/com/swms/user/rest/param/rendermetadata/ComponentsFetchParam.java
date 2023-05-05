package com.swms.user.rest.param.rendermetadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 查询渲染菜单参数
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@ApiModel("查询渲染菜单参数")
public class ComponentsFetchParam {

    /**
     * 所属系统
     */
    @ApiModelProperty(name = "systemCodes", value = "所属系统（IWMS/ESS/ALGO）", required = true)
    private List<String> systemCodes;
}
