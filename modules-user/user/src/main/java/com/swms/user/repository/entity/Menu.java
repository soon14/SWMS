package com.swms.user.repository.entity;

import com.swms.utils.base.BaseUserPO;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单模型表
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
)
@Accessors(chain = true)
public class Menu extends BaseUserPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    /**
     * 所属系统 ess/iwms/algo
     */
    @ApiModelProperty("所属系统 ESS/IWMS/ALGO")
    @Column(nullable = false, columnDefinition = "varchar(64) comment '所属系统 ESS/IWMS/ALGO'")
    private String systemCode;

    /**
     * 父菜单id,如果是顶级菜单, 则为0
     */
    @ApiModelProperty("父菜单id,如果是顶级菜单, 则为0")
    @Column(nullable = false, columnDefinition = "bigint comment '父菜单id,如果是顶级菜单, 则为0'")
    private Long parentId;

    /**
     * 类型(1: 系统、2: 菜单、3: 权限)
     */
    @ApiModelProperty("类型(1: 系统、2: 菜单、3: 权限)")
    @Column(nullable = false, columnDefinition = "int comment '类型(1: 系统、2: 菜单、3: 权限)'")
    private Integer type;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @Column(nullable = false, columnDefinition = "varchar(128) comment '名称'")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Column(columnDefinition = "varchar(255) comment '描述'")
    private String description;


    /**
     * 权限标识, 多个可用逗号分隔
     */
    @ApiModelProperty("权限标识, 多个可用逗号分隔")
    @Column(nullable = false, columnDefinition = "varchar(255) comment '权限标识, 多个可用逗号分隔'")
    private String permissions;

    /**
     * 排序，数字越小越靠前
     */
    @ApiModelProperty("排序，数字越小越靠前")
    @Column(nullable = false, columnDefinition = "int comment '排序，数字越小越靠前'")
    private Integer orderNum;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    @Column(columnDefinition = "varchar(64) comment '图标'")
    private String icon;

    /**
     * 路径地址
     */
    @ApiModelProperty("路径地址")
    @Column(columnDefinition = "varchar(128) comment '路径地址'")
    private String path;

    /**
     * 是否启用(1启用, 0禁用)
     */
    @ApiModelProperty("是否启用(1启用, 0禁用)")
    @Column(nullable = false, columnDefinition = "int comment '是否启用(1启用, 0禁用)'")
    private Integer enable;


    @ApiModelProperty("子节点")
    @Transient
    private List<Menu> children;
}
