package com.swms.inbound.domain.repository;

import com.swms.inbound.domain.entity.AcceptOrder;

import java.util.List;

public interface AcceptOrderRepository {

    void saveOrderAndDetail(AcceptOrder acceptOrder);

    List<AcceptOrder> findByInboundPlanOrderId(Long inboundPlanOrderId);
}
