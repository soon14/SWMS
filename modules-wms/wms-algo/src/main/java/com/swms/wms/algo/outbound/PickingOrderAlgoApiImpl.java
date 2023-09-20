package com.swms.wms.algo.outbound;

import com.swms.wms.api.algo.IPickingOrderAlgoApi;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderDTO;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class PickingOrderAlgoApiImpl implements IPickingOrderAlgoApi {

    @Override
    public List<PickingOrderAssignedResult> assignOrders(PickingOrderHandlerContext pickingOrderHandlerContext) {

        List<WorkStationDTO> workStations = pickingOrderHandlerContext.getWorkStations()
            .stream()
            .filter(v -> v.getWorkStationStatus() == WorkStationStatusEnum.ONLINE)
            .filter(v -> CollectionUtils.isNotEmpty(v.getPutWalls())).toList();

        if (CollectionUtils.isEmpty(workStations)) {
            return Collections.emptyList();
        }

        List<PutWallDTO.PutWallSlot> putWallSlots = workStations
            .stream().flatMap(workStationDTO -> workStationDTO.getPutWalls().stream())
            .flatMap(putWallDTO -> putWallDTO.getPutWallSlots().stream())
            .filter(putWallSlot -> putWallSlot.getPutWallSlotStatus() == PutWallSlotStatusEnum.IDLE)
            .toList();

        for (PickingOrderDTO pickingOrderDTO : pickingOrderHandlerContext.getPickingOrders()) {
            for (PutWallDTO.PutWallSlot putWallSlot : putWallSlots) {
                if (putWallSlot.getPutWallSlotStatus() != PutWallSlotStatusEnum.IDLE) {
                    continue;
                }
                pickingOrderDTO.setAssignedStationSlot(Map.of(putWallSlot.getWorkStationId(), putWallSlot.getPutWallSlotCode()));
                putWallSlot.setPutWallSlotStatus(PutWallSlotStatusEnum.BOUND);
            }
        }

        return pickingOrderHandlerContext.getPickingOrders()
            .stream().filter(v -> MapUtils.isNotEmpty(v.getAssignedStationSlot()))
            .map(v -> new PickingOrderAssignedResult()
                .setPickingOrderNo(v.getPickingOrderNo())
                .setAssignedStationSlot(v.getAssignedStationSlot())).toList();
    }

    @Override
    public List<OperationTaskDTO> allocateStocks(PickingOrderHandlerContext pickingOrderHandlerContext) {

        Map<Long, List<ContainerStockDTO>> skuBatchStockMap = pickingOrderHandlerContext.getContainerStocks()
            .stream().collect(Collectors.groupingBy(ContainerStockDTO::getSkuBatchStockId));

        return pickingOrderHandlerContext.getPickingOrders().stream()
            .flatMap(pickingOrder -> allocateSinglePickingOrder(pickingOrder, skuBatchStockMap)).toList();
    }

    private Stream<OperationTaskDTO> allocateSinglePickingOrder(PickingOrderDTO pickingOrder,
                                                                Map<Long, List<ContainerStockDTO>> skuBatchStockMap) {
        return pickingOrder.getDetails().stream().flatMap(detail -> {
            if (detail.getQtyRequired() <= 0) {
                return null;
            }
            List<ContainerStockDTO> containerStockDTOS = skuBatchStockMap.get(detail.getSkuBatchStockId());
            if (CollectionUtils.isEmpty(containerStockDTOS)) {
                return null;
            }
            return containerStockDTOS.stream().filter(v -> v.getAvailableQty() > 0).flatMap(containerStockDTO -> {
                int qtyPreAllocated = Math.min(detail.getQtyRequired(), containerStockDTO.getAvailableQty());
                detail.setQtyRequired(detail.getQtyRequired() - qtyPreAllocated);
                containerStockDTO.setAvailableQty(containerStockDTO.getAvailableQty() - qtyPreAllocated);

                return Stream.of(new OperationTaskDTO()
                    .setSkuId(detail.getSkuId())
                    .setSkuBatchStockId(detail.getSkuBatchStockId())
                    .setSourceContainerCode(containerStockDTO.getContainerCode())
                    .setSourceContainerSlot(containerStockDTO.getContainerSlotCode())
                    .setAssignedStationSlot(pickingOrder.getAssignedStationSlot())
                    .setContainerStockId(containerStockDTO.getId())
                    .setRequiredQty(qtyPreAllocated)
                    .setTaskType(OperationTaskTypeEnum.PICKING)
                    .setWarehouseCode(pickingOrder.getWarehouseCode())
                    .setOrderId(pickingOrder.getId())
                    .setDetailId(detail.getId()));
            });
        }).filter(Objects::nonNull);
    }
}
