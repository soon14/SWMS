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
 * 渲染元数据
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RenderMetadata extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 页面ID，对应menu表中的id(type=2)
     */
    @ApiModelProperty(name = "renderId", value = "页面ID，对应menu表中的id(type=2)")
    private Long renderId;


    /**
     * 页面渲染metadata内容
     */
    @ApiModelProperty(name = "renderContent", value = "页面渲染metadata内容")
    private String renderContent;

    /**
     * 前端渲染模板，对应menu表中render_template(type=2)
     */
    @ApiModelProperty(name = "renderTemplate", value = "渲染模板Code")
    private String renderTemplate;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述")
    private String description;
}
