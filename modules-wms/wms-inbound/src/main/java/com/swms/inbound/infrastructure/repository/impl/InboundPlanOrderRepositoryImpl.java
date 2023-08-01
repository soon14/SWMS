package com.swms.inbound.infrastructure.repository.impl;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.infrastructure.persistence.mapper.InboundPlanOrderDetailPORepository;
import com.swms.inbound.infrastructure.persistence.mapper.InboundPlanOrderPORepository;
import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderDetailPO;
import com.swms.inbound.infrastructure.persistence.po.InboundPlanOrderPO;
import com.swms.inbound.infrastructure.persistence.transfer.InboundPlanOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InboundPlanOrderRepositoryImpl implements InboundPlanOrderRepository {

    @Autowired
    private InboundPlanOrderPORepository inboundPlanOrderPORepository;

    @Autowired
    private InboundPlanOrderDetailPORepository inboundPlanOrderDetailPORepository;

    @Autowired
    private InboundPlanOrderPOTransfer inboundPlanOrderPOTransfer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderAndDetail(InboundPlanOrder inboundPlanOrder) {
        InboundPlanOrderPO inboundPlanOrderPO = inboundPlanOrderPORepository.save(inboundPlanOrderPOTransfer.toOrderPO(inboundPlanOrder));

        List<InboundPlanOrderDetailPO> inboundPlanOrderDetailPOS = inboundPlanOrderPOTransfer.toDetailPOs(inboundPlanOrder.getInboundPlanOrderDetails());
        inboundPlanOrderDetailPOS.forEach(v -> v.setInboundPlanOrderId(inboundPlanOrderPO.getId()));
        inboundPlanOrderDetailPORepository.saveAll(inboundPlanOrderDetailPOS);
    }

    @Override
    public InboundPlanOrder findById(Long inboundPlanOrderId) {
        return null;
    }

    @Override
    public List<InboundPlanOrder> findByCustomerOrderNo(String customerOrderNo) {
        return null;
    }
}
