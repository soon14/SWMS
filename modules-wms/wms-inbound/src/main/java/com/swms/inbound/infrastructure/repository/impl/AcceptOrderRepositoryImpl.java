package com.swms.inbound.infrastructure.repository.impl;

import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.repository.AcceptOrderRepository;
import com.swms.inbound.infrastructure.persistence.mapper.AcceptOrderDetailPORepository;
import com.swms.inbound.infrastructure.persistence.mapper.AcceptOrderPORepository;
import com.swms.inbound.infrastructure.persistence.po.AcceptOrderPO;
import com.swms.inbound.infrastructure.persistence.transfer.AcceptOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcceptOrderRepositoryImpl implements AcceptOrderRepository {

    @Autowired
    private AcceptOrderPORepository acceptOrderPORepository;
    @Autowired
    private AcceptOrderDetailPORepository acceptOrderDetailPORepository;

    @Autowired
    private AcceptOrderPOTransfer acceptOrderPOTransfer;

    @Override
    @Transactional
    public void saveOrderAndDetail(AcceptOrder acceptOrder) {
        AcceptOrderPO acceptOrderPO = acceptOrderPORepository.save(acceptOrderPOTransfer.toPO(acceptOrder));
        acceptOrder.getAcceptOrderDetails().forEach(v -> v.setAcceptOrderId(acceptOrderPO.getId()));
        acceptOrderDetailPORepository.saveAll(acceptOrderPOTransfer.toDetailPOs(acceptOrder.getAcceptOrderDetails()));
    }

    @Override
    public List<AcceptOrder> findByInboundPlanOrderId(Long inboundPlanOrderId) {
        return null;
    }
}
