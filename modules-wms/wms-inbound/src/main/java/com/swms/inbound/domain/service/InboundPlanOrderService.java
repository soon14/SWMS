package com.swms.inbound.domain.service;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;

import java.util.Set;

public interface InboundPlanOrderService {
    Set<SkuMainDataDTO> validateInboundPlanOrder(InboundPlanOrder inboundPlanOrder);

    void validateRepeatCustomerOrderNo(InboundPlanOrder inboundPlanOrder);

    void validateRepeatBoxNo(InboundPlanOrder inboundPlanOrder);
}
