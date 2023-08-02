package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.ReceiveOrderCreateTypeEnum;
import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import com.swms.wms.api.inbound.dto.ReceiveOrderDetailDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ReceiveOrder {

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

    private List<ReceiveOrderDetailDTO> receiveOrderDetails;
}
