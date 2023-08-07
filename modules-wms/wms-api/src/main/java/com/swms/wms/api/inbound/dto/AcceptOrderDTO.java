package com.swms.wms.api.inbound.dto;

import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AcceptOrderDTO {

    private Long id;

    @Size(max = 64)
    private String orderNo;

    private Long inboundPlanOrderId;

    @NotEmpty
    @Size(max = 64)
    private String warehouseCode;
    @NotEmpty
    @Size(max = 64)
    private String ownerCode;

    @NotNull
    private AcceptMethodEnum acceptMethod;
    @NotNull
    private AcceptTypeEnum acceptType;

    private boolean putAway;

    private Long totalQty;
    private Integer totalBox;

    private String remark;

    private AcceptOrderStatusEnum acceptOrderStatus;

    @NotEmpty
    private List<AcceptOrderDetailDTO> acceptOrderDetails;

}
