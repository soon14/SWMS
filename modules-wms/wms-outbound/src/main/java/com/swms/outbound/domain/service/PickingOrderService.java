package com.swms.outbound.domain.service;

import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.task.dto.OperationTaskDTO;

import java.util.List;

public interface PickingOrderService {

    /**
     * split a wave into a list of picking orders
     *
     * @param outboundWave outboundWave
     *
     * @return
     */
    List<PickingOrder> spiltWave(OutboundWave outboundWave);

    /**
     * assign picking orders to some work stations based on the requirements
     *
     * @param pickingOrderHandlerContext pickingOrderHandlerContext
     *
     * @return
     */
    List<PickingOrderAssignedResult> assignOrders(PickingOrderHandlerContext pickingOrderHandlerContext);

    /**
     * allocate stocks for the picking orders
     *
     * @param pickingOrders pickingOrders
     *
     * @return
     */
    List<OperationTaskDTO> allocateStocks(PickingOrderHandlerContext pickingOrders);
}
