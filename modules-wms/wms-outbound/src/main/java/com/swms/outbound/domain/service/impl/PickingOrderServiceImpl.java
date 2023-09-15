package com.swms.outbound.domain.service.impl;

import com.swms.common.utils.id.OrderNoGenerator;
import com.swms.outbound.domain.entity.OutboundOrderPlanPreAllocatedRecord;
import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.entity.PickingOrderDetail;
import com.swms.outbound.domain.repository.OutboundPreAllocatedRecordRepository;
import com.swms.outbound.domain.service.PickingOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PickingOrderServiceImpl implements PickingOrderService {

    @Autowired
    private OutboundPreAllocatedRecordRepository outboundPreAllocatedRecordRepository;

    @Override
    public List<PickingOrder> spiltWave(OutboundWave outboundWave) {

        return outboundWave.getOrders().stream().map(order -> {

            List<OutboundOrderPlanPreAllocatedRecord> records = outboundPreAllocatedRecordRepository.findByOutboundPlanOrderId(order.getId());
            List<PickingOrderDetail> pickingOrderDetails = records.stream().map(preAllocatedRecord ->
                new PickingOrderDetail().setSkuId(preAllocatedRecord.getSkuId())
                    .setOutboundOrderPlanId(order.getId())
                    .setBatchAttributes(preAllocatedRecord.getBatchAttributes())
                    .setQtyRequired(preAllocatedRecord.getQtyPreAllocated())
                    .setSkuBatchId(preAllocatedRecord.getSkuBatchId())
                    .setOutboundOrderPlanDetailId(preAllocatedRecord.getOutboundPlanOrderDetailId())).toList();

            return new PickingOrder()
                .setPickingOrderNo(OrderNoGenerator.generationPickingOrderNo())
                .setWaveNo(order.getWaveNo())
                .setDetails(pickingOrderDetails);
        }).toList();
    }
}
