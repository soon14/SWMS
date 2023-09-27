package com.swms.wms.outbound.application.scheduler;

import static com.swms.common.utils.constants.RedisConstants.NEW_PICKING_ORDER_IDS;

import com.alibaba.ttl.TtlRunnable;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.wms.outbound.domain.aggregate.PickingOrderTaskAggregate;
import com.swms.wms.outbound.domain.entity.PickingOrder;
import com.swms.wms.outbound.domain.repository.PickingOrderRepository;
import com.swms.wms.outbound.domain.service.PickingOrderService;
import com.swms.wms.outbound.domain.transfer.PickingOrderTransfer;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderDTO;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.outbound.event.NewOperationTaskEvent;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PickingOrderHandleScheduler {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DomainEventPublisher publisher;

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
    @Qualifier("pickingOrderHandleExecutor")
    private Executor pickingOrderHandleExecutor;

    @Scheduled(cron = "*/5 * * * * *")
    public void pickingOrderHandle() {

        List<String> keys = redisUtils.keys(NEW_PICKING_ORDER_IDS);
        keys.forEach(key -> {
            List<String> pickingOrderNos = redisUtils.getList(key);
            if (CollectionUtils.isEmpty(pickingOrderNos)) {
                return;
            }

            CompletableFuture.runAsync(Objects.requireNonNull(TtlRunnable.get(() ->
                this.handlePickingOrders(pickingOrderNos, key))), pickingOrderHandleExecutor);
        });
    }

    public void handlePickingOrders(List<String> pickingOrderNos, String key) {

        String warehouseCode = key.substring(key.lastIndexOf("_") + 1);
        List<WorkStationDTO> workStationDTOS = workStationApi.getByWarehouseCode(warehouseCode)
            .stream().filter(v -> v.getWorkStationStatus() == WorkStationStatusEnum.ONLINE).toList();

        if (CollectionUtils.isEmpty(workStationDTOS)) {
            return;
        }

        boolean anyMatch = workStationDTOS.stream()
            .flatMap(v -> v.getPutWalls().stream())
            .flatMap(v -> v.getPutWallSlots().stream())
            .anyMatch(v -> v.getPutWallSlotStatus() == PutWallSlotStatusEnum.IDLE);
        if (!anyMatch) {
            return;
        }

        List<PickingOrder> pickingOrders = pickingOrderRepository.findByPickingOrderNos(pickingOrderNos)
            .stream().filter(v -> v.getPickingOrderStatus() == PickingOrderStatusEnum.NEW).toList();

        if (CollectionUtils.isEmpty(pickingOrders)) {
            redisUtils.removeList(key, pickingOrderNos);
            return;
        }

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

        redisUtils.removeList(key, pickingOrderHandlerContext.getPickingOrders()
            .stream().map(PickingOrderDTO::getPickingOrderNo).toList());
    }
}
