package com.swms.wms.outbound.domain.repository;

import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;

import java.util.List;

public interface OutboundPlanOrderRepository {

    OutboundPlanOrder saveOutboundPlanOrder(OutboundPlanOrder outboundPlanOrder);

    void saveAll(List<OutboundPlanOrder> outboundPlanOrders);

    OutboundPlanOrder findByOrderNo(String orderNo);

    OutboundPlanOrder findById(Long orderId);

    List<OutboundPlanOrder> findAllByIds(List<Long> orderIds);

    List<OutboundPlanOrder> findByCustomerOrderNo(String customerOrderNo);

    void saveOrderAndDetails(List<OutboundPlanOrder> outboundPlanOrders);
}
