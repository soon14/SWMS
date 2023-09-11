package com.swms.wms.api.outbound;

import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import jakarta.validation.Valid;

public interface IOutboundPlanOrderApi {

    void createOutboundPlanOrder(@Valid OutboundPlanOrderDTO outboundPlanOrderDTO);

    void wavePicking();
}
