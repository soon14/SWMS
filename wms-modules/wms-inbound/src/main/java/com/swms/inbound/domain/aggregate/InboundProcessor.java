package com.swms.inbound.domain.aggregate;

import com.google.common.base.Preconditions;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.entity.ReceiveOrder;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InboundProcessor {

    @Autowired
    private InboundPlanOrder inboundPlanOrder;

    @Autowired
    private ReceiveOrder receiveOrder;

    public void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO) {
        inboundPlanOrder.createInboundPlanOrder(inboundPlanOrderDTO);
    }

    public void cancelInboundPlanOrder(Long inboundPlanOrderId) {
        int count = inboundPlanOrder.cancelInboundPlanOrder(inboundPlanOrderId);
        Preconditions.checkState(count != 1, "cancelled inbound plan order error, because status is not NEW.");
    }

}
