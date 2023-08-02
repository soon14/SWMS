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
 * 系统用户角色关联表
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "u_user_role",
    indexes = {
        @Index(name = "idx_user_id", columnList = "userId"),
    }
)
@Accessors(chain = true)
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @Column(nullable = false, columnDefinition = "bigInt(11) comment '用户id'")
    private Long userId;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @Column(nullable = false, columnDefinition = "bigInt(11) comment '角色id'")
    private Long roleId;

}
