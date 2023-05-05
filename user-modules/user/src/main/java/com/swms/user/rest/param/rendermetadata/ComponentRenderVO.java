package com.swms.user.rest.param.rendermetadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author sws
 * @Date 2021/3/22 18:22
 * @Description:
 */
@Data
@ApiModel("组件渲染元数据")
public class ComponentRenderVO {
    @ApiModelProperty(name = "id", value = "id")
    private Long id;

    @ApiModelProperty(name = "title", value = "名称")
    private String title;

    @ApiModelProperty(name = "type", value = "类型（menu/component/page）")
    private String type;

    @ApiModelProperty(name = "linkUrl", value = "链接")
    private String linkUrl;

    @ApiModelProperty(name = "icon", value = "icon")
    private String icon;

    @ApiModelProperty(name = "renderTemplate", value = "渲染模板Code")
    private String renderTemplate;

    @ApiModelProperty(name = "description", value = "描述")
    private String description;

    @ApiModelProperty(name = "systemCode", value = "所属系统")
    private String systemCode;

    @ApiModelProperty(name = "children", value = "子级菜单")
    private List<ComponentRenderVO> children;
}
