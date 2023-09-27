package com.swms.wms.outbound.domain.aggregate;

import com.swms.common.utils.id.OrderNoGenerator;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.entity.OutboundWave;
import com.swms.wms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.wms.outbound.domain.repository.OutboundWaveRepository;
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

    @Transactional(rollbackFor = Exception.class)
    public String waveOrders(List<OutboundPlanOrder> outboundPlanOrders) {
        String waveNo = OrderNoGenerator.generationOutboundWaveNo();
        outboundWaveRepository.save(new OutboundWave(waveNo, outboundPlanOrders));

        outboundPlanOrders.forEach(v -> v.setWaveNo(waveNo));
        outboundPlanOrderRepository.saveAll(outboundPlanOrders);
        return waveNo;
    }
}
