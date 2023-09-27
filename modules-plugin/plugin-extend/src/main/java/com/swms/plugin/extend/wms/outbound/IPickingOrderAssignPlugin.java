package com.swms.plugin.extend.wms.outbound;

import com.swms.plugin.sdk.extensions.IPlugin;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;

import java.util.List;

public interface IPickingOrderAssignPlugin extends IPlugin<PickingOrderHandlerContext, List<PickingOrderAssignedResult>> {

    List<PickingOrderAssignedResult> doOperation(PickingOrderHandlerContext pickingOrderHandlerContext);
}
