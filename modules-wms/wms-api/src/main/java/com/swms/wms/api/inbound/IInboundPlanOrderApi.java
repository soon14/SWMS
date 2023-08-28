package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IInboundPlanOrderApi {
    void createInboundPlanOrder(@Valid List<InboundPlanOrderDTO> inboundPlanOrderDTOs);
}
