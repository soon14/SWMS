package com.swms.inbound.domain.aggregate;

import com.google.common.base.Preconditions;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.transfer.InboundPlanOrderTransfer;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundProcessor {

    private InboundPlanOrderRepository inboundPlanOrderRepository;
    private InboundPlanOrderTransfer inboundPlanOrderTransfer;

    public void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO) {
        inboundPlanOrderRepository.save(inboundPlanOrderTransfer.toInboundPlanOrder(inboundPlanOrderDTO));
    }

    public void cancelInboundPlanOrder(Long inboundPlanOrderId) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(inboundPlanOrderId);
        Preconditions.checkState(inboundPlanOrder != null, "cancelled inbound plan order error, because order is not exist.");
        inboundPlanOrder.cancel();
        inboundPlanOrderRepository.save(inboundPlanOrder);
    }

    public void beginReceiving(Long inboundPlanOrderId) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(inboundPlanOrderId);
        inboundPlanOrder.beginReceiving();
        inboundPlanOrderRepository.save(inboundPlanOrder);
    }

    public void receive() {

    }

}
