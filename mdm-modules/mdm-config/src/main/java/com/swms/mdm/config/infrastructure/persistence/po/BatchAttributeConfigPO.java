package com.swms.mdm.config.infrastructure.persistence.po;

import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.config.infrastructure.persistence.converter.ListBatchAttributeFieldConfigConverter;
import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_batch_attribute_config_code", columnList = "batchAttributeCode")
    }
)
public class BatchAttributeConfigPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", type = IdGenerator.class)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '编码'")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '名称'")
    private String name;

    @Column(columnDefinition = "varchar(64) comment '货主编码'")
    private String ownerCode;
    @Column(columnDefinition = "varchar(255) comment '商品大类'")
    private String skuFirstCategory;

    private boolean enable;

    @Column(columnDefinition = "json comment '参数配置'")
    @Convert(converter = ListBatchAttributeFieldConfigConverter.class)
    private List<BatchAttributeConfigDTO.BatchAttributeFieldConfigDTO> batchAttributeFieldConfigs;

    @Version
    private Long version;
}
