package com.swms.user.rest.param.rendertemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

/**
 * <p>
 * 增加渲染模板参数
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@ApiModel("添加模板参数")
public class RenderTemplateAddParam {
    /**
     * 模板名称
     */
    @ApiModelProperty(name = "name", value = "模板名称", required = true)
    @NotEmpty(message = "模板名称不能为空")
    private String name;

    /**
     * 模板Code
     */
    @ApiModelProperty(name = "code", value = "模板Code", required = true)
    @NotEmpty(message = "模板Code不能为空")
    private String code;

    /**
     * 验证schema
     */
    @ApiModelProperty(name = "validateSchema", value = "验证schema", required = true)
    @NotEmpty(message = "验证schema不能为空")
    private String validateSchema;

    /**
     * 所属系统
     */
    @ApiModelProperty(name = "systemCode", value = "所属系统（参考枚举SystemEnum）", required = true)
    @NotEmpty(message = "所属系统不能为空")
    private String systemCode;

    /**
     * 描述
     */
    @ApiModelProperty(name = "description", value = "描述")
    private String description;
}
