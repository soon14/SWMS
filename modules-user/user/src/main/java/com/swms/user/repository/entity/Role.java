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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p>
 * 系统角色表
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
        @Index(unique = true, name = "idx_role_code", columnList = "code")
    }
)
@DynamicUpdate
@Accessors(chain = true)
public class Role extends BaseUserPO {

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @Column(nullable = false, columnDefinition = "varchar(128) comment '角色名称'")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @Column(nullable = false, columnDefinition = "varchar(64) comment '角色编码'")
    private String code;

    /**
     * 状态（1启用, 0停用）
     */
    @ApiModelProperty("状态（1启用, 0停用）")
    @Column(nullable = false, columnDefinition = "int comment '状态（1启用, 0停用）'")
    private Integer status;

    /**
     * 角色描述
     */
    @ApiModelProperty("描述")
    @Column(columnDefinition = "varchar(255) comment '描述'")
    private String description;

}
