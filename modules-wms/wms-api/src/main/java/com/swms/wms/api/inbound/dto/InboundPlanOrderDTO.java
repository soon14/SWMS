package com.swms.wms.api.inbound.dto;

import lombok.Data;

import java.util.List;

@Data
public class InboundPlanOrderDTO {
    private List<InboundPlanOrderDetailDTO> inboundPlanOrderDetails;
}
