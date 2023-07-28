package com.swms.user.rest.param.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author sws
 * @Date 2021/3/24 16:36
 * @Description:
 */
@Data
@ApiModel("查询枚举数据")
public class EnumFetchParam {
    @ApiModelProperty(name = "enumNames", value = "枚举Key")
    @NotEmpty(message = "枚举Key不能为空")
    private String enumNames;
}
