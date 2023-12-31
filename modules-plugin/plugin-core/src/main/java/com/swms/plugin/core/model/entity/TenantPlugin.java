package com.swms.plugin.core.model.entity;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.plugin.api.constants.TenantPluginStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "p_tenant_plugin",
    indexes = {
        @Index(unique = true, name = "idx_tenant_plugin", columnList = "tenantName,pluginId")
    }
)
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantPlugin extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '租户名称'")
    private String tenantName;

    @Column(nullable = false, columnDefinition = "bigint comment '插件ID'")
    private Long pluginId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '插件编号'")
    private String pluginCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private TenantPluginStatusEnum status;
}
