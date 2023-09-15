package com.swms.outbound.domain.service;

import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;

import java.util.List;

public interface PickingOrderService {
    List<PickingOrder> spiltWave(OutboundWave outboundWave);

    List<PickingOrder> assignOrders(List<PickingOrder> pickingOrders, List<WorkStationDTO> workStationDTOS);

    List<OperationTaskDTO> allocateStocks(List<PickingOrder> pickingOrders);
}
