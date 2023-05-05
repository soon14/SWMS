package com.swms.user.rest.param.oauthclient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询参数
 *
 * @author sws
 * @version 1.0
 */
@Data
@ApiModel("查询参数")
public class OauthClientFetchParam {

    @ApiModelProperty(name = "clientId", value = "客户端Id", required = true)
    private String clientId;

}
