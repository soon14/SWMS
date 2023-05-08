package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IInboundApi {
    void createInboundPlanOrder(@NotNull InboundPlanOrderDTO inboundPlanOrderDTO);

    void accept(@NotNull AcceptRecordDTO acceptRecord);

    void completeAccepting(@NotNull Long acceptOrderId);
}
