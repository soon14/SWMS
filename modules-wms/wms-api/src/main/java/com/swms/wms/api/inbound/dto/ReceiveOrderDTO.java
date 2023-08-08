package com.swms.wms.api.inbound.dto;

import com.swms.wms.api.inbound.constants.ReceiveOrderCreateTypeEnum;
import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ReceiveOrderDTO {

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
    @NotEmpty
    private String receiveOrderType;

    @NotNull
    private StorageTypeEnum storageType;
    @NotNull
    private ReceiveOrderCreateTypeEnum createType;

    private ReceiveOrderStatusEnum receiveOrderStatus;

    @NotEmpty
    private List<ReceiveOrderDetailDTO> receiveOrderDetails;

    private Long version;
}
