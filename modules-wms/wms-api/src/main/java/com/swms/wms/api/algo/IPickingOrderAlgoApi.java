package com.swms.wms.api.algo;

import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IPickingOrderAlgoApi {

    List<PickingOrderAssignedResult> assignOrders(@Valid PickingOrderHandlerContext pickingOrderHandlerContext);

    List<OperationTaskDTO> allocateStocks(@Valid PickingOrderHandlerContext pickingOrderHandlerContext);
}
