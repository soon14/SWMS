package com.swms.outbound.application;

import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.aggregate.OutboundWaveAggregate;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.domain.service.OutboundPlanOrderService;
import com.swms.outbound.domain.transfer.OutboundPlanOrderTransfer;
import com.swms.wms.api.outbound.IOutboundPlanOrderApi;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import com.swms.wms.api.outbound.event.NewOutboundPlanOrderEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class OutboundPlanOrderApiImpl implements IOutboundPlanOrderApi {

    @Autowired
    private OutboundPlanOrderTransfer outboundPlanOrderTransfer;

    @Autowired
    private OutboundPlanOrderService outboundPlanOrderService;

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;


    @Override
    public void createOutboundPlanOrder(OutboundPlanOrderDTO outboundPlanOrderDTO) {

        OutboundPlanOrder outboundPlanOrder = outboundPlanOrderTransfer.toDO(outboundPlanOrderDTO);

        outboundPlanOrder.initialize();
        outboundPlanOrderService.validateOutboundPlanOrder(outboundPlanOrder);

        outboundPlanOrderRepository.saveOutboundPlanOrder(outboundPlanOrder);

        domainEventPublisher.sendAsyncEvent(new NewOutboundPlanOrderEvent(outboundPlanOrder.getOrderNo()));
    }

    @Override
    public void wavePicking() {
        List<OutboundWaveAggregate> outboundWaves = outboundPlanOrderService.wavePicking();

        if (CollectionUtils.isEmpty(outboundWaves)) {
            return;
        }
        outboundWaves.forEach(OutboundWaveAggregate::save);
    }
}
