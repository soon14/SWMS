package com.swms.extend.outbound;

import com.swms.plugin.sdk.extensions.IPlugin;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import com.swms.wms.api.outbound.dto.OutboundWaveDTO;

import java.util.List;

public interface IOutboundWavePickingPlugin extends IPlugin<List<OutboundPlanOrderDTO>, List<List<OutboundPlanOrderDTO>>> {

    List<List<OutboundPlanOrderDTO>> doOperation(List<OutboundPlanOrderDTO> outboundPlanOrder);
}
