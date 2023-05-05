package com.swms.user.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends BaseEntity {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码(加密后)
     */
    @ApiModelProperty("密码(加密后)")
    private String password;

    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty("帐号状态（1启用, 0停用）")
    private Integer status;

    /**
     * 是否被锁(小于等于5表示未被锁, 大于5表示被锁)
     */
    @ApiModelProperty("是否被锁(小于等于5表示未被锁, 大于5表示被锁)")
    private Integer locked;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatar;

    /**
     * 上一次登录的ip地址
     */
    @ApiModelProperty("上一次登录的ip地址")
    private String lastLoginIp;

    /**
     * 上一次登录的时间
     */
    @ApiModelProperty("上一次登录的时间")
    private String lastGmtLoginTime;

    /**
     * 账号标识,默认为 NORMAL:普通账号
     */
    @ApiModelProperty("账号标识,默认为 NORMAL:普通账号")
    private String type;
}
