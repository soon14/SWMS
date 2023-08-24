package com.swms.inbound.domain.service;

import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.entity.InboundPlanOrderDetail;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;

import java.util.List;

public interface AcceptOrderService {

    InboundPlanOrder findAcceptInboundPlanOrder(AcceptRecordDTO acceptRecord);

    void validateOverAccept(AcceptRecordDTO acceptRecord, List<AcceptOrder> acceptOrders,
                            InboundPlanOrderDetail inboundPlanOrderDetail, InboundPlanOrder inboundPlanOrder);

    void validateMultipleArrivals(List<AcceptOrder> acceptOrders, InboundPlanOrder inboundPlanOrder);
}
