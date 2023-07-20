package com.swms.user.repository.entity;

import com.swms.utils.base.BaseUserPO;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_username", columnList = "username"),
        @Index(unique = true, name = "idx_phone", columnList = "phone")
    }
)
@DynamicUpdate
@Accessors(chain = true)
public class User extends BaseUserPO {

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    @Column(nullable = false, columnDefinition = "varchar(128) comment '用户名称'")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @Column(nullable = false, columnDefinition = "varchar(64) comment '手机号'")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Column(columnDefinition = "varchar(128) comment '邮箱'")
    private String email;

    /**
     * 用户名
     */
    @ApiModelProperty("登录用户名")
    @Column(nullable = false, columnDefinition = "varchar(128) comment '登录用户名'")
    private String username;

    /**
     * 密码(加密后)
     */
    @ApiModelProperty("密码(加密后)")
    @Column(nullable = false, columnDefinition = "varchar(128) comment '密码(加密后)'")
    private String password;

    /**
     * 帐号状态（1启用, 0停用）
     */
    @ApiModelProperty("帐号状态（1启用, 0停用）")
    @Column(nullable = false, columnDefinition = "int comment '状态（1启用, 0停用）'")
    private Integer status;

    /**
     * 是否被锁(小于等于5表示未被锁, 大于5表示被锁)
     */
    @ApiModelProperty("是否被锁(小于等于5表示未被锁, 大于5表示被锁)")
    @Column(nullable = false, columnDefinition = "int comment '是否被锁(小于等于5表示未被锁, 大于5表示被锁)'")
    private Integer locked = 0;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    @Column(columnDefinition = "varchar(128) comment '头像地址'")
    private String avatar;

    /**
     * 上一次登录的ip地址
     */
    @ApiModelProperty("上一次登录的ip地址")
    @Column(columnDefinition = "varchar(64) comment '上一次登录的ip地址'")
    private String lastLoginIp;

    /**
     * 上一次登录的时间
     */
    @ApiModelProperty("上一次登录的时间")
    @Column(columnDefinition = "varchar(64) comment '上一次登录的时间'")
    private String lastGmtLoginTime;

    /**
     * 账号标识,默认为 NORMAL:普通账号
     */
    @ApiModelProperty("账号标识,默认为 NORMAL:普通账号")
    @Column(nullable = false, columnDefinition = "varchar(64) comment '账号标识,默认为 NORMAL:普通账号'")
    private String type = "NORMAL";
}
