package com.swms.user.repository.entity;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author sws
 * @since 2021-01-29
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(name = "idx_username", columnList = "username")
    }
)
@Accessors(chain = true)
public class LoginLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @Column(nullable = false, columnDefinition = "varchar(128) comment '用户名称'")
    private String username;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    @Column(nullable = false, columnDefinition = "varchar(64) comment '登录时间'")
    private String gmtLoginTime;

    /**
     * 登录的时间戳
     */
    @ApiModelProperty("登录的时间戳")
    @Column(nullable = false, columnDefinition = "bigint comment '登录的时间戳'")
    private Long gmtLoginTimestamp;

    /**
     * 登录ip
     */
    @ApiModelProperty("登录ip")
    @Column(nullable = false, columnDefinition = "varchar(64) comment '登录ip'")
    private String loginIp;

    /**
     * 登录结果(1成功, 2失败)
     */
    @ApiModelProperty("登录结果(1成功, 2失败)")
    @Column(nullable = false, columnDefinition = "int comment '登录结果(1成功, 2失败)'")
    private Integer loginResult;

    /**
     * 登录地址
     */
    @ApiModelProperty("登录地址")
    @Column(nullable = false, columnDefinition = "varchar(255) comment '登录地址'")
    private String loginAddress = "";

    /**
     * 登录失败原因
     */
    @ApiModelProperty("登录失败原因")
    @Column(nullable = false, columnDefinition = "varchar(255) comment '登录失败原因'")
    private String loginFailureMsg = "";

}
