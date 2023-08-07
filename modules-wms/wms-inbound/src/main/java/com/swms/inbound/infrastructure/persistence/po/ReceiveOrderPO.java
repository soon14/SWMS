package com.swms.inbound.infrastructure.persistence.po;

import com.swms.common.utils.base.AuditUserPO;
import com.swms.wms.api.inbound.constants.ReceiveOrderCreateTypeEnum;
import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_receive_order",
    indexes = {
        @Index(unique = true, name = "idx_order_no", columnList = "orderNo")
    }
)
@DynamicUpdate
public class ReceiveOrderPO extends AuditUserPO {

    private Long id;

    @Size(max = 64)
    private String orderNo;

    @NotEmpty
    @Size(max = 64)
    private String warehouseCode;
    @NotEmpty
    @Size(max = 64)
    private String ownerCode;

    @Size(max = 64)
    private String receiveOrderType;
    private StorageTypeEnum storageType;
    private ReceiveOrderCreateTypeEnum createType;

    private ReceiveOrderStatusEnum receiveOrderStatus;

}
