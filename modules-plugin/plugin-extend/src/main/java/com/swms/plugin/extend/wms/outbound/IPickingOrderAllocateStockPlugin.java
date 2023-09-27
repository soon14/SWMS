package com.swms.plugin.extend.wms.outbound;

import com.swms.plugin.sdk.extensions.IPlugin;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.task.dto.OperationTaskDTO;

import java.util.List;

public interface IPickingOrderAllocateStockPlugin extends IPlugin<PickingOrderHandlerContext, List<OperationTaskDTO>> {

    List<OperationTaskDTO> doOperation(PickingOrderHandlerContext pickingOrderHandlerContext);
}
