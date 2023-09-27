package com.swms.wms.inbound.infrastructure.repository.impl;

import com.swms.wms.inbound.domain.entity.AcceptOrder;
import com.swms.wms.inbound.domain.repository.AcceptOrderRepository;
import com.swms.wms.inbound.infrastructure.persistence.mapper.AcceptOrderDetailPORepository;
import com.swms.wms.inbound.infrastructure.persistence.mapper.AcceptOrderPORepository;
import com.swms.wms.inbound.infrastructure.persistence.po.AcceptOrderDetailPO;
import com.swms.wms.inbound.infrastructure.persistence.po.AcceptOrderPO;
import com.swms.wms.inbound.infrastructure.persistence.transfer.AcceptOrderPOTransfer;
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
        acceptOrder.getDetails().forEach(v -> v.setAcceptOrderId(acceptOrderPO.getId()));
        acceptOrderDetailPORepository.saveAll(acceptOrderPOTransfer.toDetailPOs(acceptOrder.getDetails()));
    }

    @Override
    public List<AcceptOrder> findByInboundPlanOrderId(Long inboundPlanOrderId) {
        List<AcceptOrderPO> acceptOrderPOS = acceptOrderPORepository.findByInboundPlanOrderId(inboundPlanOrderId);

        return acceptOrderPOS.stream().map(v ->
            acceptOrderPOTransfer.toDO(v, acceptOrderDetailPORepository.findByAcceptOrderId(v.getId()))
        ).toList();
    }

    @Override
    public AcceptOrder findById(Long acceptOrderId) {

        AcceptOrderPO acceptOrder = acceptOrderPORepository.findById(acceptOrderId).orElseThrow();
        List<AcceptOrderDetailPO> acceptOrderDetails = acceptOrderDetailPORepository.findByAcceptOrderId(acceptOrder.getId());

        return acceptOrderPOTransfer.toDO(acceptOrder, acceptOrderDetails);
    }

    @Override
    public void saveOrder(AcceptOrder acceptOrder) {
        acceptOrderPORepository.save(acceptOrderPOTransfer.toPO(acceptOrder));
    }
}
