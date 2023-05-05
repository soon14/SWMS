package com.swms.user.rest.param.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 添加菜单参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("添加菜单参数")
public class MenuAddParam {
    /**
     * 所属系统 ess/iwms/algo
     */
    @ApiModelProperty(name = "systemCode", value = "所属系统（参考枚举SystemEnum）")
    private String systemCode;

    /**
     * 父菜单id
     */
    @ApiModelProperty(name = "parentId", value = "父菜单id, 如果为顶级菜单, 则设置为空")
    private Long parentId;

    /**
     * 类型(1: 系统、2: 菜单、3: 权限)
     */
    @ApiModelProperty(name = "type", value = "菜单类型（1: 系统、2: 菜单、3: 权限，参考枚举MenuTypeEnum）", required = true)
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 名称
     */
    @ApiModelProperty(name = "title", value = "菜单名称", required = true)
    @NotEmpty(message = "菜单名称不能为空")
    @Length(max = 128, message = "名称不能超过128位")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述")
    @Length(max = 256, message = "描述不能超过256位")
    private String description;

    /**
     * 权限标识, 多个可用逗号分隔
     */
    @ApiModelProperty(name = "permissions", value = "权限标识", required = true)
    @NotEmpty(message = "权限标识不能为空")
    @Length(max = 128, message = "权限标识不能超过128位")
    private String permissions;

    /**
     * vue组件名称, 只有类型为1、2才有值
     */
    @ApiModelProperty(name = "component", value = "vue组件名称")
    private String component;

    /**
     * 排序，数字越小越靠前
     */
    @ApiModelProperty(name = "orderNum", value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    @Range(min = 0, max = 1000000000, message = "排序值必须为1000000000以内的非负整数")
    private Integer orderNum;

    /**
     * 图标
     */
    @ApiModelProperty(name = "icon", value = "图标")
    @Length(max = 32, message = "图标不能超过32位")
    private String icon;

    /**
     * 路径地址
     */
    @ApiModelProperty(name = "path", value = "路径地址")
    @Length(max = 256, message = "路径地址不能超过256位")
    private String path;

    /**
     * 页面渲染模板
     */
    @ApiModelProperty(name = "renderTemplate", value = "页面渲染模板（type=2时必填）")
    private String renderTemplate;

    /**
     * extensionData 扩展属性
     */
    @ApiModelProperty("extensionData 扩展属性")
    @Length(max = 64, message = "扩展属性不能超过64位")
    private String extensionData;

    /**
     * 样式 (1:左侧菜单,2:卡片)
     */
    @ApiModelProperty("样式(1:左侧菜单,2:卡片)")
    private Integer style;
}
