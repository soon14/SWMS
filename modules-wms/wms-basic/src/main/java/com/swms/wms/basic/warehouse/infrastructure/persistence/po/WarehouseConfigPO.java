package com.swms.wms.basic.warehouse.infrastructure.persistence.po;

import com.swms.mdm.api.config.dto.WarehouseMainDataConfigDTO;
import com.swms.common.utils.base.UpdateUserPO;
import com.swms.wms.basic.warehouse.infrastructure.persistence.converter.WarehouseMainDataConfigDTOConverter;
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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_warehouse_config",
    indexes = {
        @Index(unique = true, name = "idx_warehouse_code", columnList = "warehouseCode")
    }
)
@DynamicUpdate
public class WarehouseConfigPO extends UpdateUserPO {


    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    private String warehouseCode;

    @Convert(converter = WarehouseMainDataConfigDTOConverter.class)
    private WarehouseMainDataConfigDTO warehouseMainDataConfig;

    @Version
    private long version;
}
