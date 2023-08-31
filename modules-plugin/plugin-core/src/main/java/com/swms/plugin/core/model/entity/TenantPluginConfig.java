package com.swms.plugin.core.model.entity;

import com.swms.common.utils.base.UpdateUserPO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "p_tenant_plugin_config",
    indexes = {
        @Index(unique = true, name = "idx_tenant_plugin_config", columnList = "tenantName,pluginCode")
    }
)
@DynamicUpdate
public class TenantPluginConfig extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '租户名称'")
    private String tenantName;

    @Column(nullable = false, columnDefinition = "bigint comment '插件ID'")
    private String pluginCode;

    @Column(columnDefinition = "text comment '插件配置'")
    private String configInfo = "";

    @Version
    private Long version;
}
