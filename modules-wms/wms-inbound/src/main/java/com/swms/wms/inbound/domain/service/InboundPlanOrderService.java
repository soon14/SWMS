package com.swms.wms.inbound.domain.service;

import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;

import java.util.List;
import java.util.Set;

public interface InboundPlanOrderService {
    Set<SkuMainDataDTO> validateInboundPlanOrder(List<InboundPlanOrder> inboundPlanOrder);


}
