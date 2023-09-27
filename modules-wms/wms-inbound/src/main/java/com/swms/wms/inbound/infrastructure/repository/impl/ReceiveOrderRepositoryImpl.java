package com.swms.wms.inbound.infrastructure.repository.impl;

import com.swms.wms.inbound.domain.entity.ReceiveOrder;
import com.swms.wms.inbound.domain.repository.ReceiveOrderRepository;
import com.swms.wms.inbound.infrastructure.persistence.mapper.ReceiveOrderDetailPORepository;
import com.swms.wms.inbound.infrastructure.persistence.mapper.ReceiveOrderPORepository;
import com.swms.wms.inbound.infrastructure.persistence.transfer.ReceiveOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveOrderRepositoryImpl implements ReceiveOrderRepository {

    @Autowired
    private ReceiveOrderPORepository receiveOrderPORepository;

    @Autowired
    private ReceiveOrderPOTransfer receiveOrderPOTransfer;

    @Autowired
    private ReceiveOrderDetailPORepository receiveOrderDetailPORepository;

    @Override
    public void saveOrderAndDetail(ReceiveOrder receiveOrder) {
        receiveOrderPORepository.save(receiveOrderPOTransfer.toPO(receiveOrder));
        receiveOrderDetailPORepository.saveAll(receiveOrderPOTransfer.toDetailPOs(receiveOrder.getReceiveOrderDetails()));
    }

    @Override
    public ReceiveOrder findById(Long receiveOrderId) {
        return null;
    }
}
