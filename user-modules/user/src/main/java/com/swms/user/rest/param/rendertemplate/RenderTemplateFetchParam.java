package com.swms.user.rest.param.rendertemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * 查询渲染模板参数
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@ApiModel("查询渲染模板参数")
public class RenderTemplateFetchParam {

    /**
     * 页面ID，对应menu表中的id(type=2)
     */
    @ApiModelProperty(name = "code", value = "模板Code", required = true)
    @NotNull(message = "模板Code不能为空")
    private String code;
}
