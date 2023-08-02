package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import jakarta.validation.Valid;

public interface IInboundPlanOrderApi {
    void createInboundPlanOrder(@Valid InboundPlanOrderDTO inboundPlanOrderDTO);
}
