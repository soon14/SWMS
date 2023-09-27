package com.swms.wms.outbound.application.event;

import com.google.common.eventbus.Subscribe;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.wms.outbound.domain.aggregate.PickingOrderWaveAggregate;
import com.swms.wms.outbound.domain.entity.OutboundWave;
import com.swms.wms.outbound.domain.entity.PickingOrder;
import com.swms.wms.outbound.domain.repository.OutboundWaveRepository;
import com.swms.wms.outbound.domain.repository.PickingOrderRepository;
import com.swms.wms.api.outbound.constants.OutboundWaveStatusEnum;
import com.swms.wms.api.outbound.event.NewOutboundWaveEvent;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import com.swms.wms.api.outbound.event.OrderPickingEvent;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import com.swms.wms.api.task.event.OperationTaskFinishedEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PickingOrderSubscribe {

    @Autowired
    private OutboundWaveRepository outboundWaveRepository;

    @Autowired
    private PickingOrderRepository pickingOrderRepository;

    @Autowired
    private PickingOrderWaveAggregate pickingOrderWaveAggregate;

    @Autowired
    private DomainEventPublisher publisher;

    @Subscribe
    public void onCreateEvent(@Valid NewOutboundWaveEvent event) {
        OutboundWave outboundWave = outboundWaveRepository.findByWaveNo(event.getWaveNo());
        if (outboundWave.getWaveStatus() != OutboundWaveStatusEnum.NEW) {
            return;
        }
        List<PickingOrder> pickingOrders = pickingOrderWaveAggregate.split(outboundWave);

        publisher.sendAsyncEvent(new NewPickingOrdersEvent()
            .setPickingOrderNos(pickingOrders.stream().map(PickingOrder::getPickingOrderNo).toList())
            .setWarehouseCode(outboundWave.getWarehouseCode()));
    }


    @Subscribe
    public void onPickingEvent(@Valid OperationTaskFinishedEvent event) {
        List<OperationTaskDTO> operationTasks = event.getOperationTasks()
            .stream().filter(v -> v.getTaskType() == OperationTaskTypeEnum.PICKING).toList();

        if (CollectionUtils.isEmpty(operationTasks)) {
            return;
        }

        List<Long> pickingOrderIds = operationTasks.stream().map(OperationTaskDTO::getOrderId).toList();
        List<PickingOrder> pickingOrders = pickingOrderRepository.findByPickingOrderIds(pickingOrderIds);

        List<OrderPickingEvent.PickingDetail> pickedDetails = pickingOrderWaveAggregate.picking(pickingOrders, operationTasks);

        publisher.sendAsyncEvent(new OrderPickingEvent().setPickingDetails(pickedDetails));
    }
}
