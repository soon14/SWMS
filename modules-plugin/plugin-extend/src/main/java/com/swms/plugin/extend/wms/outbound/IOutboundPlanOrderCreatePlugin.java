package com.swms.plugin.extend.wms.outbound;

import com.swms.plugin.sdk.extensions.IPlugin;
import com.swms.plugin.sdk.extensions.OperationContext;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;

public interface IOutboundPlanOrderCreatePlugin extends IPlugin<OutboundPlanOrderDTO, Void> {

    @Override
    void beforeDoOperation(OperationContext<OutboundPlanOrderDTO> operationContext);

    @Override
    void afterDoOperation(OperationContext<OutboundPlanOrderDTO> operationContext);
}
