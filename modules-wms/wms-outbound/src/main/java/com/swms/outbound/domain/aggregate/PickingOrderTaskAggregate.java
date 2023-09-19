package com.swms.outbound.domain.aggregate;

import com.google.common.collect.Lists;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import com.swms.wms.api.stock.dto.ContainerStockLockDTO;
import com.swms.wms.api.task.ITaskApi;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PickingOrderTaskAggregate {

    @Autowired
    private ITaskApi taskApi;

    @Autowired
    private PickingOrderRepository pickingOrderRepository;

    @Autowired
    private IStockApi stockApi;

    @Autowired
    private IPutWallApi putWallApi;

    @Transactional(rollbackFor = Exception.class)
    public void createTasks(List<OperationTaskDTO> operationTaskDTOS, List<PickingOrderAssignedResult> assignedResults) {

        List<ContainerStockLockDTO> lockDTOS = operationTaskDTOS.stream()
            .map(v -> ContainerStockLockDTO.builder().lockType(StockLockTypeEnum.OUTBOUND)
                .lockQty(v.getOperatedQty()).taskId(v.getId())
                .containerStockId(v.getContainerStockId())
                .build()).toList();
        stockApi.lockContainerStock(lockDTOS);

        Map<String, Map<Long, String>> assignedMap = assignedResults.stream()
            .collect(Collectors.toMap(PickingOrderAssignedResult::getPickingOrderNo, PickingOrderAssignedResult::getAssignedStationSlot));
        List<PickingOrder> pickingOrders = pickingOrderRepository.findByPickingOrderNos(assignedMap.keySet());
        pickingOrders.forEach(v -> v.dispatch(assignedMap.get(v.getPickingOrderNo())));
        pickingOrderRepository.saveAll(pickingOrders);

        List<AssignOrdersDTO.AssignDetail> assignDetails = Lists.newArrayList();
        pickingOrders.forEach(v -> v.getAssignedStationSlot().forEach((key, value) -> {
            AssignOrdersDTO.AssignDetail assignDetail = new AssignOrdersDTO.AssignDetail().
                setOrderId(v.getId()).setPutWallSlotCode(value)
                .setWorkStationId(key);
            assignDetails.add(assignDetail);
        }));

        putWallApi.assignOrders(new AssignOrdersDTO().setAssignDetails(assignDetails));

        taskApi.createOperationTasks(operationTaskDTOS);
    }
}
