package com.swms.extend.outbound;

import com.swms.plugin.sdk.extensions.IPlugin;
import com.swms.plugin.sdk.extensions.OperationContext;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;

public interface IOutboundPlanOrderCreatePlugin extends IPlugin<OutboundPlanOrderDTO, Void> {

    void beforeDoOperation(OperationContext<OutboundPlanOrderDTO> operationContext);

    void afterDoOperation(OperationContext<OutboundPlanOrderDTO> operationContext);
}
