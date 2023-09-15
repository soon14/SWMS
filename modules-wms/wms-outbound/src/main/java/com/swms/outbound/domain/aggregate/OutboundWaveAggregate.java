package com.swms.outbound.domain.aggregate;

import com.swms.common.utils.id.OrderNoGenerator;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.wms.api.outbound.event.NewOutboundWaveEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutboundWaveAggregate {

    @Autowired
    private OutboundWaveRepository outboundWaveRepository;

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void waveOrders(List<OutboundPlanOrder> outboundPlanOrders) {
        String waveNo = OrderNoGenerator.generationOutboundWaveNo();
        outboundWaveRepository.save(new OutboundWave(waveNo, outboundPlanOrders));

        outboundPlanOrders.forEach(v -> v.setWaveNo(waveNo));
        outboundPlanOrderRepository.saveAll(outboundPlanOrders);

        domainEventPublisher.sendAsyncEvent(new NewOutboundWaveEvent().setWaveNo(waveNo));
    }
}
