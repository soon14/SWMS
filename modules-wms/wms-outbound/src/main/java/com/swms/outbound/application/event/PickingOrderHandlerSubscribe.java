package com.swms.outbound.application.event;

import static com.swms.common.utils.constants.RedisConstants.NEW_PICKING_ORDER_IDS;

import com.google.common.eventbus.Subscribe;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.outbound.domain.aggregate.PickingOrderTaskAggregate;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.outbound.domain.service.PickingOrderService;
import com.swms.outbound.domain.transfer.PickingOrderTransfer;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderDTO;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.outbound.event.NewOperationTaskEvent;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PickingOrderHandlerSubscribe {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PickingOrderRepository pickingOrderRepository;

    @Autowired
    private PickingOrderService pickingOrderService;

    @Autowired
    private IWorkStationApi workStationApi;

    @Autowired
    private PickingOrderTransfer pickingOrderTransfer;

    @Autowired
    private PickingOrderTaskAggregate pickingOrderTaskAggregate;

    @Autowired
    private DomainEventPublisher publisher;

    @Subscribe
    public void onEvent(@Valid NewPickingOrdersEvent event) {

        String redisKey = NEW_PICKING_ORDER_IDS + event.getWarehouseCode();

        redisUtils.pushAll(redisKey, event.getPickingOrderNos());
        List<String> allPickingOrderNos = redisUtils.getList(redisKey);

        List<PickingOrder> pickingOrders = pickingOrderRepository.findByPickingOrderNos(allPickingOrderNos);
        List<WorkStationDTO> workStationDTOS = workStationApi.getByWarehouseCode(event.getWarehouseCode());

        PickingOrderHandlerContext pickingOrderHandlerContext = new PickingOrderHandlerContext()
            .setPickingOrders(pickingOrderTransfer.toDTOs(pickingOrders))
            .setWorkStations(workStationDTOS);
        List<PickingOrderAssignedResult> orderAssignedResults = pickingOrderService.assignOrders(pickingOrderHandlerContext);

        if (CollectionUtils.isEmpty(orderAssignedResults)) {
            return;
        }

        Set<String> assignedPickingOrderNos = orderAssignedResults.stream().map(PickingOrderAssignedResult::getPickingOrderNo)
            .collect(Collectors.toSet());
        pickingOrderHandlerContext.getPickingOrders().removeIf(v -> !assignedPickingOrderNos.contains(v.getPickingOrderNo()));
        pickingOrderHandlerContext.getPickingOrders().forEach(v -> orderAssignedResults.forEach(assignedResult -> {
            if (StringUtils.equals(assignedResult.getPickingOrderNo(), v.getPickingOrderNo())) {
                v.setAssignedStationSlot(assignedResult.getAssignedStationSlot());
            }
        }));

        List<OperationTaskDTO> operationTaskDTOS = pickingOrderService.allocateStocks(pickingOrderHandlerContext);

        pickingOrderTaskAggregate.createTasks(operationTaskDTOS, orderAssignedResults);

        publisher.sendAsyncEvent(new NewOperationTaskEvent().setOperationTasks(operationTaskDTOS));

        redisUtils.removeList(redisKey, pickingOrderHandlerContext.getPickingOrders()
            .stream().map(PickingOrderDTO::getPickingOrderNo).toList());
    }
}
