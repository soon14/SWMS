package com.swms.user.rest.param.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 导航模型
 *
 * @author sws
 * @version 1.0
 * @since 2020-12-31
 */
@Data
@ApiModel("导航模型")
public class NavigationVo {

    @ApiModelProperty("导航信息")
    private List<NavigationInfo> navigationInfos;
}
