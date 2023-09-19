package com.swms.outbound.domain.aggregate;

import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.outbound.domain.service.PickingOrderService;
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

    @Transactional(rollbackFor = Exception.class)
    public List<PickingOrder> split(OutboundWave outboundWave) {
        List<PickingOrder> pickingOrders = outboundPickingOrderService.spiltWave(outboundWave);
        pickingOrderRepository.saveOrderAndDetails(pickingOrders);

        outboundWave.process();
        outboundWaveRepository.save(outboundWave);

        return pickingOrders;
    }
}
