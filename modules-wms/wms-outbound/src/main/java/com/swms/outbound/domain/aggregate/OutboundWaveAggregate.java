package com.swms.outbound.domain.aggregate;

import com.google.common.base.Preconditions;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.outbound.domain.transfer.OutboundWaveTransfer;
import com.swms.wms.api.outbound.event.NewOutboundWaveEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutboundWaveAggregate {

    private String waveNo;

    private List<OutboundPlanOrder> outboundPlanOrders;

    public OutboundWaveAggregate(String waveNo, List<OutboundPlanOrder> outboundPlanOrders) {
        Preconditions.checkNotNull(waveNo);
        Preconditions.checkState(CollectionUtils.isNotEmpty(outboundPlanOrders));

        this.waveNo = waveNo;
        this.outboundPlanOrders = outboundPlanOrders;
    }

    @Autowired
    private OutboundWaveRepository outboundWaveRepository;

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private OutboundWaveTransfer outboundWaveTransfer;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Transactional
    public void save() {
        outboundWaveRepository.save(outboundWaveTransfer.toDO(this));

        this.outboundPlanOrders.forEach(v -> v.setWaveNo(this.waveNo));
        outboundPlanOrderRepository.saveAll(outboundPlanOrders);

        domainEventPublisher.sendAsyncEvent(new NewOutboundWaveEvent().setWaveNo(waveNo));
    }
}
