package com.swms.outbound.domain.aggregate;

import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.outbound.domain.service.PickingOrderService;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PickingOrderWaveAggregate {

    @Autowired
    private PickingOrderService outboundPickingOrderService;

    @Autowired
    private PickingOrderRepository pickingOrderRepository;

    @Autowired
    private OutboundWaveRepository outboundWaveRepository;

    @Autowired
    private DomainEventPublisher publisher;

    @Transactional(rollbackFor = Exception.class)
    public void split(OutboundWave outboundWave) {
        List<PickingOrder> pickingOrders = outboundPickingOrderService.spiltWave(outboundWave);
        pickingOrderRepository.saveAll(pickingOrders);

        outboundWave.process();
        outboundWaveRepository.save(outboundWave);

        publisher.sendAsyncEvent(new NewPickingOrdersEvent()
            .setPickingOrderNos(pickingOrders.stream().map(PickingOrder::getPickingOrderNo).toList())
            .setWarehouseCode(outboundWave.getWarehouseCode()));
    }
}
