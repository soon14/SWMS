package com.swms.user.rest.param.rendermetadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * 查询渲染元数据参数
 * </p>
 *
 * @author sws
 * @since 2021-3-22
 */
@Data
@ApiModel("查询渲染元数据参数")
public class RenderMetadataFetchParam {

    /**
     * 页面ID，对应menu表中的id(type=2)
     */
    @ApiModelProperty(name = "renderId", value = "页面ID", required = true)
    @NotNull(message = "renderId不能为空")
    private Long renderId;
}
