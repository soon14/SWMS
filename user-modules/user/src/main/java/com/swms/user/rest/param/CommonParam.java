package com.swms.user.rest.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author sws
 * @Date 2021/3/24 10:03
 * @Description:
 */
@Data
@ApiModel("通用参数")
public class CommonParam {
    @ApiModelProperty(name = "ids", value = "ids", required = true)
    @NotNull(message = "ids不能为空")
    private List<Long> ids;
}
