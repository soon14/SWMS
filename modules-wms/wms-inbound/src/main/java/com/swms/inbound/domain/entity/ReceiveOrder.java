package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.ReceiveOrderCreateTypeEnum;
import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import com.swms.wms.api.inbound.dto.ReceiveOrderDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class ReceiveOrder {

    private Long id;

    private String orderNo;

    private String warehouseCode;
    private String ownerCode;

    private String receiveOrderType;
    private StorageTypeEnum storageType;
    private ReceiveOrderCreateTypeEnum createType;

    private ReceiveOrderStatusEnum receiveOrderStatus;

    private List<ReceiveOrderDetailDTO> receiveOrderDetails;
}
