package com.swms.user.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author sws
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    private String gmtLoginTime;

    /**
     * 登录的时间戳
     */
    @ApiModelProperty("登录的时间戳")
    private Long gmtLoginTimestamp;

    /**
     * 登录ip
     */
    @ApiModelProperty("登录ip")
    private String loginIp;

    /**
     * 登录结果(1成功, 2失败)
     */
    @ApiModelProperty("登录结果(1成功, 2失败)")
    private Integer loginResult;

    /**
     * 登录地址
     */
    @ApiModelProperty("登录地址")
    private String loginAddress;

    /**
     * 登录失败原因
     */
    @ApiModelProperty("登录失败原因")
    private String loginFailureMsg;

}
