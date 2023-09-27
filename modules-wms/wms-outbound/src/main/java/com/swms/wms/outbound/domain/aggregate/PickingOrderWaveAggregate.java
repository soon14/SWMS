package com.swms.wms.outbound.domain.aggregate;

import com.swms.wms.outbound.domain.entity.OutboundWave;
import com.swms.wms.outbound.domain.entity.PickingOrder;
import com.swms.wms.outbound.domain.entity.PickingOrderDetail;
import com.swms.wms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.wms.outbound.domain.repository.PickingOrderRepository;
import com.swms.wms.outbound.domain.service.PickingOrderService;
import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
import com.swms.wms.api.outbound.event.OrderPickingEvent;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Transactional(rollbackFor = Exception.class)
    public List<OrderPickingEvent.PickingDetail> picking(List<PickingOrder> pickingOrders, List<OperationTaskDTO> operationTasks) {

        List<Long> detailIds = operationTasks.stream().map(OperationTaskDTO::getDetailId).distinct().toList();

        pickingOrders.forEach(pickingOrder -> pickingOrder.getDetails()
            .removeIf(pickingOrderDetail -> !detailIds.contains(pickingOrderDetail.getId())));

        Map<Long, PickingOrder> pickingOrderMap = pickingOrders.stream().collect(Collectors.toMap(PickingOrder::getId, v -> v));
        Map<Long, PickingOrderDetail> pickingOrderDetailMap = pickingOrders.stream().flatMap(v -> v.getDetails().stream())
            .collect(Collectors.toMap(PickingOrderDetail::getId, v -> v));

        List<OrderPickingEvent.PickingDetail> pickedDetails = operationTasks.stream().map(operationTaskDTO -> {
            pickingOrderMap.get(operationTaskDTO.getOrderId()).picking(operationTaskDTO.getOperatedQty(), operationTaskDTO.getDetailId());

            PickingOrderDetail pickingOrderDetail = pickingOrderDetailMap.get(operationTaskDTO.getDetailId());
            return new OrderPickingEvent.PickingDetail().setOperatedQty(operationTaskDTO.getOperatedQty())
                .setOutboundOrderDetailId(pickingOrderDetail.getOutboundOrderPlanDetailId())
                .setOutboundOrderId(pickingOrderDetail.getOutboundOrderPlanId());
        }).toList();

        pickingOrderRepository.saveOrderAndDetails(pickingOrders);

        List<String> waveNos = pickingOrders.stream()
            .filter(v -> v.getPickingOrderStatus() == PickingOrderStatusEnum.PICKED)
            .map(PickingOrder::getWaveNo).distinct().toList();
        if (CollectionUtils.isEmpty(waveNos)) {
            return pickedDetails;
        }

        List<OutboundWave> outboundWaves = outboundWaveRepository.findByWaveNos(waveNos);
        Map<String, OutboundWave> outboundWaveMap = outboundWaves
            .stream().collect(Collectors.toMap(OutboundWave::getWaveNo, v -> v));

        List<PickingOrder> wavePickingOrders = pickingOrderRepository.findByWaveNos(waveNos);
        Map<String, List<PickingOrder>> waveMap = wavePickingOrders.stream().collect(Collectors.groupingBy(PickingOrder::getWaveNo));

        waveMap.forEach((waveNo, orders) -> {
            if (orders.stream().allMatch(v -> v.getPickingOrderStatus() == PickingOrderStatusEnum.PICKED
                || v.getPickingOrderStatus() == PickingOrderStatusEnum.CANCELED)) {
                outboundWaveMap.get(waveNo).done();
            }
        });
        outboundWaveRepository.saveAll(outboundWaves);

        return pickedDetails;
    }
}
