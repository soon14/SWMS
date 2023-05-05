package com.swms.user.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author sws
 * @Date 2020/12/18 15:00
 * @Description: 枚举值
 */
@Data
@Builder
public class EnumDTO {
    @ApiModelProperty("枚举Key")
    private String enumName;

    @ApiModelProperty("枚举值")
    private String code;

    @ApiModelProperty("枚举描述（显示）")
    private String desc;
}
