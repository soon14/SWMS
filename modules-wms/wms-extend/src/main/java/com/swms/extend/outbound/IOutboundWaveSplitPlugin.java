package com.swms.extend.outbound;

import com.swms.plugin.sdk.extensions.IPlugin;
import com.swms.plugin.sdk.extensions.OperationContext;
import com.swms.wms.api.outbound.dto.OutboundWaveDTO;
import com.swms.wms.api.outbound.dto.PickingOrderDTO;

import java.util.List;

public interface IOutboundWaveSplitPlugin extends IPlugin<OutboundWaveDTO, List<PickingOrderDTO>> {

    List<PickingOrderDTO> doOperation(OperationContext<OutboundWaveDTO> operationContext);
}
