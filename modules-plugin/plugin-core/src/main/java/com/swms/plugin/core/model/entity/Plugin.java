package com.swms.plugin.core.model.entity;

import com.swms.common.utils.base.UpdateUserPO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 插件
 * </p>
 *
 * @author guizhigang
 * @since 2021-02-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "p_plugin",
    indexes = {
        @Index(unique = true, name = "idx_code_version", columnList = "code,version")
    }
)
@DynamicUpdate
public class Plugin extends UpdateUserPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '插件编号'")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '插件名称'")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '开发者'")
    private String developer;

    /**
     * 版本 （主版本.次版本.bugfix版本）
     */
    @Column(nullable = false, columnDefinition = "varchar(64) comment '插件版本'")
    private String version;

    /**
     * 所属模块（wms/station/user）
     */
    @Column(nullable = false, columnDefinition = "varchar(32) comment '所属于系统'")
    private String system;

    /**
     * 所属模块（outbound/inbound）
     */
    @Column(nullable = false, columnDefinition = "varchar(32) comment '所属于模块'")
    private String module;

    /**
     * 依赖关系（JSON存储）如：[{"pluginId":"plugin1","startVersion":"2.0.1","endVersion":"2.0.6"}]
     */
    @Column(nullable = false, columnDefinition = "varchar(128) comment '依赖关系'")
    private String dependencies = "";

    /**
     * 描述
     */
    @Column(nullable = false, columnDefinition = "varchar(256) comment '描述'")
    private String description = "";

    /**
     * 系统要求
     */
    @Column(nullable = false, columnDefinition = "varchar(256) comment '系统要求'")
    private String systemRequest = "";

    @Column(nullable = false, columnDefinition = "varchar(256) comment '系统要求'")
    private String jarFilePath;

    @Column(nullable = false, columnDefinition = "varchar(256) comment '系统要求'")
    private String configFilePath = "";
}
