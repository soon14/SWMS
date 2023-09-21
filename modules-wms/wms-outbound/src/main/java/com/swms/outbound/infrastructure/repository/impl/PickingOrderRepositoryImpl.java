package com.swms.outbound.infrastructure.repository.impl;

import com.google.common.collect.Lists;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.entity.PickingOrderDetail;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.outbound.infrastructure.persistence.mapper.PickingOrderDetailPORepository;
import com.swms.outbound.infrastructure.persistence.mapper.PickingOrderPORepository;
import com.swms.outbound.infrastructure.persistence.po.PickingOrderDetailPO;
import com.swms.outbound.infrastructure.persistence.po.PickingOrderPO;
import com.swms.outbound.infrastructure.persistence.transfer.PickingOrderDetailPOTransfer;
import com.swms.outbound.infrastructure.persistence.transfer.PickingOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return transferPickingOrders(pickingOrderPORepository.findAllByPickingOrderNoIn(pickingOrderNos));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderAndDetails(List<PickingOrder> pickingOrders) {
        Map<String, PickingOrderPO> pickingOrderPOMap = pickingOrderPORepository
            .saveAll(pickingOrderPOTransfer.toPOs(pickingOrders))
            .stream().collect(Collectors.toMap(PickingOrderPO::getPickingOrderNo, v -> v));
        pickingOrders.forEach(pickingOrder -> {
            PickingOrderPO pickingOrderPO = pickingOrderPOMap.get(pickingOrder.getPickingOrderNo());
            pickingOrder.getDetails().forEach(pickingOrderDetail -> pickingOrderDetail.setPickingOrderId(pickingOrderPO.getId()));

        });

        List<PickingOrderDetail> pickingOrderDetails = pickingOrders.stream().flatMap(v -> v.getDetails().stream()).toList();
        pickingOrderDetailPORepository.saveAll(pickingOrderDetailPOTransfer.toPOs(pickingOrderDetails));
    }

    @Override
    public List<PickingOrder> findByPickingOrderIds(Collection<Long> pickingOrderIds) {
        List<PickingOrderPO> pickingOrderPOS = pickingOrderPORepository.findAllById(pickingOrderIds);
        return transferPickingOrders(pickingOrderPOS);
    }

    @Override
    public List<PickingOrder> findByWaveNos(List<String> waveNos) {
        List<PickingOrderPO> pickingOrderPOS = pickingOrderPORepository.findAllByWaveNoIn(waveNos);
        return pickingOrderPOTransfer.toDOs(pickingOrderPOS);
    }

    private List<PickingOrder> transferPickingOrders(List<PickingOrderPO> pickingOrderPOS) {
        Map<Long, PickingOrderPO> pickingOrderPOMap = pickingOrderPOS.stream().collect(Collectors.toMap(PickingOrderPO::getId, v -> v));
        Map<Long, List<PickingOrderDetailPO>> pickingOrderDetailMap = pickingOrderDetailPORepository
            .findByPickingOrderIdIn(pickingOrderPOMap.keySet())
            .stream().collect(Collectors.groupingBy(PickingOrderDetailPO::getPickingOrderId));

        List<PickingOrder> pickingOrders = Lists.newArrayList();
        pickingOrderDetailMap.forEach((pickingOrderId, details) ->
            pickingOrders.add(pickingOrderPOTransfer.toDO(pickingOrderPOMap.get(pickingOrderId), details)));

        return pickingOrders;
    }
}
