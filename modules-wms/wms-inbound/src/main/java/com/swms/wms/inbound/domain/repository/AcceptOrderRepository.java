package com.swms.wms.inbound.domain.repository;

import com.swms.wms.inbound.domain.entity.AcceptOrder;

import java.util.List;

public interface AcceptOrderRepository {

    void saveOrderAndDetail(AcceptOrder acceptOrder);

    List<AcceptOrder> findByInboundPlanOrderId(Long inboundPlanOrderId);

    AcceptOrder findById(Long acceptOrderId);

    void saveOrder(AcceptOrder acceptOrder);
}
