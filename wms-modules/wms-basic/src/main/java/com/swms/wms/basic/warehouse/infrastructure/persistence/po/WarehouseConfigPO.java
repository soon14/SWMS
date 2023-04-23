package com.swms.wms.basic.warehouse.infrastructure.persistence.po;

import com.swms.mdm.api.config.dto.WarehouseMainDataConfigDTO;
import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
import com.swms.wms.basic.warehouse.infrastructure.persistence.converter.WarehouseMainDataConfigDTOConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_warehouse_code", columnList = "warehouseCode")
    }
)
public class WarehouseConfigPO extends BaseUserPO {


    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", type = IdGenerator.class)
    private Long id;

    private String warehouseCode;

    @Convert(converter = WarehouseMainDataConfigDTOConverter.class)
    private WarehouseMainDataConfigDTO warehouseMainDataConfig;
}
