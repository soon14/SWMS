package com.swms.user.rest.param.loginlog;

import com.swms.user.rest.common.param.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询参数
 *
 * @author sws
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("分页查询参数")
public class LoginLogPageParam extends PageParam {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("登录结果(参考枚举YesOrNo)")
    private Integer loginResult;

}
