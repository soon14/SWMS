package com.swms.user.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 菜单模型表
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属系统 ess/iwms/algo
     */
    @ApiModelProperty("所属系统 ESS/IWMS/ALGO")
    private String systemCode;


    /**
     * 父菜单id,如果是顶级菜单, 则为0
     */
    @ApiModelProperty("父菜单id,如果是顶级菜单, 则为0")
    private Long parentId;

    /**
     * 类型(1: 系统、2: 菜单、3: 权限)
     */
    @ApiModelProperty("类型(1: 系统、2: 菜单、3: 权限)")
    private Integer type;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;


    /**
     * 权限标识, 多个可用逗号分隔
     */
    @ApiModelProperty("权限标识, 多个可用逗号分隔")
    private String permissions;

    /**
     * vue组件名称, 只有类型为1、2才有值
     */
    @ApiModelProperty("vue组件名称, 只有类型为1、2才有值（暂未用到）")
    private String component;

    /**
     * 排序，数字越小越靠前
     */
    @ApiModelProperty("排序，数字越小越靠前")
    private Integer orderNum;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 路径地址
     */
    @ApiModelProperty("路径地址")
    private String path;

    /**
     * 页面渲染模板
     */
    @ApiModelProperty("页面渲染模板")
    private String renderTemplate;

    /**
     * 是否启用(1启用, 0禁用)
     */
    @ApiModelProperty("是否启用(1启用, 0禁用)")
    private Integer enable;

    /**
     * html target 属性(_blank, _self, _parent, _top)
     */
    @ApiModelProperty("html target 属性")
    private String htmlTarget;

    /**
     * extensionData 扩展属性
     */
    @ApiModelProperty("extensionData 扩展属性")
    private String extensionData;

    /**
     * 样式(1:左侧菜单,2:卡片)
     */
    @ApiModelProperty("样式(1:左侧菜单,2:卡片)")
    private Integer style;


    /**
     * 兼容amis
     */
    private String label;

    public String getLabel() {
        return this.title;
    }


}
