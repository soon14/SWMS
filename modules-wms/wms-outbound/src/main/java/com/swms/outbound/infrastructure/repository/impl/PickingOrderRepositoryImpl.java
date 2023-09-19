package com.swms.outbound.infrastructure.repository.impl;

import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.entity.PickingOrderDetail;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.outbound.infrastructure.persistence.mapper.PickingOrderDetailPORepository;
import com.swms.outbound.infrastructure.persistence.mapper.PickingOrderPORepository;
import com.swms.outbound.infrastructure.persistence.po.PickingOrderPO;
import com.swms.outbound.infrastructure.persistence.transfer.PickingOrderDetailPOTransfer;
import com.swms.outbound.infrastructure.persistence.transfer.PickingOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PickingOrderRepositoryImpl implements PickingOrderRepository {

    @Autowired
    private PickingOrderPORepository pickingOrderPORepository;

    @Autowired
    private PickingOrderDetailPORepository pickingOrderDetailPORepository;

    @Autowired
    private PickingOrderPOTransfer pickingOrderPOTransfer;

    @Autowired
    private PickingOrderDetailPOTransfer pickingOrderDetailPOTransfer;

    @Override
    public void saveAll(List<PickingOrder> pickingOrders) {
        pickingOrderPORepository.saveAll(pickingOrderPOTransfer.toPOs(pickingOrders));
    }

    @Override
    public List<PickingOrder> findByPickingOrderNos(Collection<String> pickingOrderNos) {
        List<PickingOrderPO> pickingOrderPOS = pickingOrderPORepository.findAllByPickingOrderNoIn(pickingOrderNos);

        return pickingOrderPOTransfer.toDOs(pickingOrderPOS);
    }

    @Override
    public void saveOrderAndDetails(List<PickingOrder> pickingOrders) {
        pickingOrderPORepository.saveAll(pickingOrderPOTransfer.toPOs(pickingOrders));
        List<PickingOrderDetail> pickingOrderDetails = pickingOrders.stream().flatMap(v -> v.getDetails().stream()).toList();
        pickingOrderDetailPORepository.saveAll(pickingOrderDetailPOTransfer.toPOs(pickingOrderDetails));
    }
}
