package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;

public interface IInboundApi {
    void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO);

    void accept(AcceptRecordDTO acceptRecord);

    void completeAccepting(Long acceptOrderId);
}
