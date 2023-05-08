package com.swms.user.rest.param.rendermetadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

/**
 * <p>
 * 增加渲染元数据参数
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@ApiModel("添加元数据参数")
public class RenderMetadataParam {

    /**
     * 页面ID，对应menu表中的id(type=2)
     */
    @ApiModelProperty(name = "renderId", value = "页面ID", required = true)
    private Long renderId;


    /**
     * 页面渲染metadata内容
     */
    @ApiModelProperty(name = "renderContent", value = "metadata内容", required = true)
    @NotEmpty(message = "渲染内容不能为空")
    private String renderContent;

    /**
     * 前端渲染模板，对应menu表中render_template(type=2)
     */
    @ApiModelProperty(name = "renderTemplate", value = "模板标识", required = true)
    @NotEmpty(message = "模板标识不能为空")
    private String renderTemplate;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述")
    private String description;
}
