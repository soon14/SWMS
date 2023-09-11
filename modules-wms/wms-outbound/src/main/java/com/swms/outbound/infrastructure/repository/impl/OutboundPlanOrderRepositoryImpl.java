package com.swms.outbound.infrastructure.repository.impl;

import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.infrastructure.persistence.mapper.OutboundPlanOrderDetailPORepository;
import com.swms.outbound.infrastructure.persistence.mapper.OutboundPlanOrderPORepository;
import com.swms.outbound.infrastructure.persistence.po.OutboundPlanOrderDetailPO;
import com.swms.outbound.infrastructure.persistence.po.OutboundPlanOrderPO;
import com.swms.outbound.infrastructure.persistence.transfer.OutboundPlanOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutboundPlanOrderRepositoryImpl implements OutboundPlanOrderRepository {

    @Autowired
    private OutboundPlanOrderPORepository outboundPlanOrderPORepository;

    @Autowired
    private OutboundPlanOrderDetailPORepository outboundPlanOrderDetailPORepository;

    @Autowired
    private OutboundPlanOrderPOTransfer outboundPlanOrderPOTransfer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOutboundPlanOrder(OutboundPlanOrder outboundPlanOrder) {
        OutboundPlanOrderPO outboundPlanOrderPO = outboundPlanOrderPORepository.save(outboundPlanOrderPOTransfer.toPO(outboundPlanOrder));

        List<OutboundPlanOrderDetailPO> outboundPlanOrderDetailPOS = outboundPlanOrderPOTransfer.toDetailPOS(outboundPlanOrder.getDetails());
        outboundPlanOrderDetailPOS.forEach(v -> v.setOutboundPlanOrderId(outboundPlanOrderPO.getId()));

        outboundPlanOrderDetailPORepository.saveAll(outboundPlanOrderDetailPOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<OutboundPlanOrder> outboundPlanOrders) {
        outboundPlanOrderPORepository.saveAll(outboundPlanOrderPOTransfer.toPOS(outboundPlanOrders));
    }
}
