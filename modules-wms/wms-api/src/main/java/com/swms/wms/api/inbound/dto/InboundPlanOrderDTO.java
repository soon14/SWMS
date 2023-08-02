package com.swms.wms.api.inbound.dto;

import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class InboundPlanOrderDTO {

    private Long id;

    @Size(max = 64)
    private String orderNo;
    @NotEmpty
    @Size(max = 64)
    private String customerOrderNo;
    @Size(max = 64)
    private String lpnCode;

    @NotEmpty
    @Size(max = 64)
    private String warehouseCode;
    @NotEmpty
    @Size(max = 64)
    private String ownerCode;

    @NotEmpty
    @Size(max = 64)
    private String inboundOrderType;

    @NotEmpty
    private StorageTypeEnum storageType;
    private boolean abnormal;

    @Size(max = 128)
    private String sender;
    @Size(max = 128)
    private String carrier;
    @Size(max = 128)
    private String shippingMethod;
    @Size(max = 128)
    private String trackingNumber;
    private Long estimatedArrivalDate;
    @Size(max = 255)
    private String remark;

    private InboundPlanOrderStatusEnum inboundPlanOrderStatus;

    private Map<String, Object> extendFields;

    private Long version;

    private List<InboundPlanOrderDetailDTO> inboundPlanOrderDetails;
}
