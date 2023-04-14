package com.swms.inbound.application;

import com.swms.inbound.domain.aggregate.InboundProcessor;
import com.swms.inbound.domain.service.InboundPlanOrderService;
import com.swms.wms.api.inbound.IInboundApi;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InboundApplicationApiImpl implements IInboundApi {

    @Autowired
    private InboundProcessor inboundProcessor;

    @Autowired
    private InboundPlanOrderService inboundPlanOrderService;

    @Override
    public void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO) {
        inboundPlanOrderService.validateCreateInboundPlanOrder(inboundPlanOrderDTO);
        inboundProcessor.createInboundPlanOrder(inboundPlanOrderDTO);
    }
}
