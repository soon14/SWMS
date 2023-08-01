package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.InboundPlanOrder;

import java.util.List;


public interface InboundPlanOrderRepository {

    void saveOrderAndDetail(InboundPlanOrder inboundPlanOrder);

    InboundPlanOrder findById(Long inboundPlanOrderId);

    List<InboundPlanOrder> findByCustomerOrderNo(String customerOrderNo);
}
