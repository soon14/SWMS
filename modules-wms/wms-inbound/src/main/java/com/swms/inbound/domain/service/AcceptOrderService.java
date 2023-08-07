package com.swms.inbound.domain.service;

import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;

import java.util.List;

public interface AcceptOrderService {

    InboundPlanOrder findAcceptInboundPlanOrder(AcceptRecordDTO acceptRecord);

    void validateOverAccept(AcceptRecordDTO acceptRecord, List<AcceptOrder> acceptOrders, InboundPlanOrderDetailDTO inboundPlanOrderDetailDTO);
}
